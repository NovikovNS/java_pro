package ru.flamexander.http.server.processors;

import ru.flamexander.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MethodNotAllowedProcessor extends AbstractProcessor {

    private static final String CONTENT_TYPE_VALUE = "text/html";

    public MethodNotAllowedProcessor() {
        super(CONTENT_TYPE_VALUE);
    }

    @Override
    public void executeRequest(HttpRequest httpRequest, OutputStream output) {
        String response = "HTTP/1.1 405 Method Not Allowed\r\n" +
            "Content-Type: " + CONTENT_TYPE_VALUE  +
            "Cache-Control: public,max-age=300" + "\r\n" +
            addSessionIdForCookieIfNeed(httpRequest) +
            "\r\n<html><body><h1>Try to use other method</h1></body></html>";
        try {
            output.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
