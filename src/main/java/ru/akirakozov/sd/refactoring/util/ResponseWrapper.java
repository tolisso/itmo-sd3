package ru.akirakozov.sd.refactoring.util;

import ru.akirakozov.sd.refactoring.dto.ProductDto;
import ru.akirakozov.sd.refactoring.exception.ResponseWrapperException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWrapper implements AutoCloseable {

    private final HttpServletResponse response;
    private final boolean wrapInHtmlPage;

    public ResponseWrapper(HttpServletResponse response, boolean wrapInHtmlPage) {
        this.response = response;
        this.wrapInHtmlPage = wrapInHtmlPage;
        if (wrapInHtmlPage) {
            println("<html><body>");
        }
    }

    private PrintWriter writer() {
        try {
            return response.getWriter();
        } catch (IOException e) {
            throw new ResponseWrapperException(e);
        }
    }

    public void println(String str) {
        writer().println(str);
    }

    public void print(ProductDto product) {
        writer().println(product.getName() + "\t" + product.getPrice() + "</br>");
    }

    @Override
    public void close() {
        if (wrapInHtmlPage) {
            println("</body></html>");
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
