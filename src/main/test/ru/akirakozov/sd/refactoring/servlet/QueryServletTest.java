package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.TestDatabaseContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author akirakozov
 */
class QueryServletTest {
    private static final String TEST_CONNECTION_URL = "jdbc:sqlite:QueryServletTest.test";
    private final QueryServlet servlet = new QueryServlet(TEST_CONNECTION_URL);
    private final TestDatabaseContext testDatabaseContext = new TestDatabaseContext(TEST_CONNECTION_URL);

    @BeforeEach
    void init() {
        testDatabaseContext.reinitDatabase();
        testDatabaseContext.executeSql("INSERT INTO PRODUCT(NAME, PRICE) VALUES ('a', 10)");
        testDatabaseContext.executeSql("INSERT INTO PRODUCT(NAME, PRICE) VALUES ('b', 2)");
    }

    @Test
    void testMax() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doReturn("max").when(request).getParameter("command");

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PrintWriter printWriter = new PrintWriter(outputStream, true)) {
            doReturn(printWriter).when(response).getWriter();
            servlet.doGet(request, response);
            String res = outputStream.toString();
            String expected = "<html><body>"
                    + System.lineSeparator()
                    + "<h1>Product with max price: </h1>"
                    + System.lineSeparator()
                    + "a\t10</br>"
                    + System.lineSeparator()
                    + "</body></html>"
                    + System.lineSeparator();
            assertEquals(expected, res);
        }
    }

    @Test
    void testMin() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doReturn("min").when(request).getParameter("command");

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PrintWriter printWriter = new PrintWriter(outputStream, true)) {
            doReturn(printWriter).when(response).getWriter();
            servlet.doGet(request, response);
            String res = outputStream.toString();
            String expected = "<html><body>"
                    + System.lineSeparator()
                    + "<h1>Product with min price: </h1>"
                    + System.lineSeparator()
                    + "b\t2</br>"
                    + System.lineSeparator()
                    + "</body></html>"
                    + System.lineSeparator();
            assertEquals(expected, res);
        }
    }

    @Test
    void testSum() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doReturn("sum").when(request).getParameter("command");

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PrintWriter printWriter = new PrintWriter(outputStream, true)) {
            doReturn(printWriter).when(response).getWriter();
            servlet.doGet(request, response);
            String res = outputStream.toString();
            String expected = "<html><body>"
                    + System.lineSeparator()
                    + "Summary price: "
                    + System.lineSeparator()
                    + "12"
                    + System.lineSeparator()
                    + "</body></html>"
                    + System.lineSeparator();
            assertEquals(expected, res);
        }
    }
}
