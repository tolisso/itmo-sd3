package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    private final ProductRepository productRepository;
    public AddProductServlet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));

        productRepository.save(name, price);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
