package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductRepository;
import ru.akirakozov.sd.refactoring.dto.ProductDto;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductRepository productRepository;

    public GetProductsServlet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("<html><body>");

        List<ProductDto> products = productRepository.findAll();
        for (ProductDto product: products) {
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
        }
        response.getWriter().println("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
