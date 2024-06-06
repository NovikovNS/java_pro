package ru.flamexander.http.server.processors;

import ru.flamexander.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultOptionsProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output, Boolean isCached) throws IOException {
        String response = "HTTP/1.1 204 No Content\r\n" +
                "Connection: keep-alive\r\n" +
                "Access-Control-Allow-Origin: *\r\n" +
                "Access-Control-Allow-Methods: POST, GET, OPTIONS, DELETE\r\n" +
                "Access-Control-Allow-Headers: *\r\n" +
                "Access-Control-Max-Age: 86400";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}