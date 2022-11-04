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

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final ProductRepository productRepository;

    public QueryServlet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");

            ProductDto maxProduct = productRepository.getMax();
            response.getWriter().println(maxProduct.getName() + "\t" + maxProduct.getPrice() + "</br>");
            response.getWriter().println("</body></html>");

        } else if ("min".equals(command)) {
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");

            ProductDto minProduct = productRepository.getMin();
            response.getWriter().println(minProduct.getName() + "\t" + minProduct.getPrice() + "</br>");
            response.getWriter().println("</body></html>");

        } else if ("sum".equals(command)) {
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");

            response.getWriter().println(productRepository.sum());
            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");

            response.getWriter().println(productRepository.count());
            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
