package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.TestDatabaseContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author akirakozov
 */
class GetProductServletTest {

    private static final String TEST_CONNECTION_URL = "jdbc:sqlite:GetProductServletTest.test";
    private final GetProductsServlet servlet = new GetProductsServlet(TEST_CONNECTION_URL);
    private final TestDatabaseContext testDatabaseContext = new TestDatabaseContext(TEST_CONNECTION_URL);

    @BeforeEach
    void init() {
        testDatabaseContext.reinitDatabase();
    }

    @Test
    void testAddProductServlet() throws IOException {
        testDatabaseContext.executeSql("INSERT INTO PRODUCT(NAME, PRICE) VALUES ('abacaba', 13)");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); PrintWriter printWriter = new PrintWriter(outputStream, true)) {
            doReturn(printWriter).when(response).getWriter();
            servlet.doGet(request, response);
            String res = outputStream.toString();
            String expected = "<html><body>"
                    + System.lineSeparator()
                    + "abacaba\t13</br>"
                    + System.lineSeparator()
                    + "</body></html>"
                    + System.lineSeparator();
            assertEquals(expected, res);
        }
    }

}
