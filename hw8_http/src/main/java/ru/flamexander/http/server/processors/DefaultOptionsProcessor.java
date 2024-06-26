package ru.flamexander.http.server.processors;

import ru.flamexander.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultOptionsProcessor extends AbstractProcessor {

    public DefaultOptionsProcessor() {
        super("");
    }

    @Override
    public void executeRequest(HttpRequest httpRequest, OutputStream output) {
        String response = "HTTP/1.1 204 No Content\r\n" +
            "Connection: keep-alive\r\n" +
            "Access-Control-Allow-Origin: *\r\n" +
            "Access-Control-Allow-Methods: POST, GET, OPTIONS, DELETE\r\n" +
            "Access-Control-Allow-Headers: *\r\n" +
            "Access-Control-Max-Age: 86400" +
            addSessionIdForCookieIfNeed(httpRequest);
        try {
            output.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
