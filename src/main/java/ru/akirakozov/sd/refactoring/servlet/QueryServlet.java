package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductRepository;
import ru.akirakozov.sd.refactoring.util.ResponseWrapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final ProductRepository productRepository;

    public QueryServlet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            max(response);
        } else if ("min".equals(command)) {
            min(response);
        } else if ("sum".equals(command)) {
            sum(response);
        } else if ("count".equals(command)) {
            count(response);
        } else {
            notFoundCommand(response, command);
        }
    }

    private void max(HttpServletResponse response) {
        try (ResponseWrapper responseWrapper = new ResponseWrapper(response, true)) {
            responseWrapper.println("<h1>Product with max price: </h1>");
            responseWrapper.print(productRepository.getMax());
        }
    }

    private void min(HttpServletResponse response) {
        try (ResponseWrapper responseWrapper = new ResponseWrapper(response, true)) {
            responseWrapper.println("<h1>Product with min price: </h1>");
            responseWrapper.print(productRepository.getMin());
        }
    }

    private void sum(HttpServletResponse response) {
        try (ResponseWrapper responseWrapper = new ResponseWrapper(response, true)) {
            responseWrapper.println("Summary price: ");
            responseWrapper.println(String.valueOf(productRepository.sum()));
        }
    }

    private void count(HttpServletResponse response) {
        try (ResponseWrapper responseWrapper = new ResponseWrapper(response, true)) {
            responseWrapper.println("Number of products: ");
            responseWrapper.println(String.valueOf(productRepository.count()));
        }
    }

    private void notFoundCommand(HttpServletResponse response, String command) {
        try (ResponseWrapper responseWrapper = new ResponseWrapper(response, false)) {
           responseWrapper.println("Unknown command: " + command);
        }
    }
}


