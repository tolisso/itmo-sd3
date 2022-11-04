package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.TestDatabaseContext;
import ru.akirakozov.sd.refactoring.database.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author akirakozov
 */
class AddProductServletTest {

    private static final String TEST_CONNECTION_URL = "jdbc:sqlite:AddProductServletTest.test";
    ProductRepository productRepository = new ProductRepository(TEST_CONNECTION_URL);
    private final AddProductServlet servlet = new AddProductServlet(productRepository);
    private final TestDatabaseContext testDatabaseContext = new TestDatabaseContext(TEST_CONNECTION_URL);

    @BeforeEach
    void init() {
        testDatabaseContext.reinitDatabase();
    }

    @Test
    void testAddProductServlet() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doReturn("aboba").when(request).getParameter("name");
        doReturn("1337").when(request).getParameter("price");
        doReturn(mock(PrintWriter.class)).when(response).getWriter();
        IOException methodException = null;
        try {
            servlet.doGet(request, response);
        } catch (IOException exc) {
            methodException = exc;
        }
        assertNull(methodException);

        try (Connection c = DriverManager.getConnection(TEST_CONNECTION_URL)) {
            Statement stmt = c.createStatement();
            ResultSet result = stmt.executeQuery("SELECT NAME, PRICE FROM PRODUCT");
            assertTrue(result.next());
            String name = result.getString("NAME");
            long price = result.getLong("PRICE");
            assertEquals("aboba", name);
            assertEquals(1337L, price);
            assertFalse(result.next());
            stmt.close();
        } catch (SQLException e) {
            assert false : "sql exception occurred while testing";
        }
    }

}
