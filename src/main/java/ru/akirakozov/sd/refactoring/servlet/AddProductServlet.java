package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductRepository;
import ru.akirakozov.sd.refactoring.util.ResponseWrapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    private final ProductRepository productRepository;
    public AddProductServlet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (ResponseWrapper responseWrapper = new ResponseWrapper(response, false)) {
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));

            productRepository.save(name, price);
            responseWrapper.println("OK");
        }
    }
}
