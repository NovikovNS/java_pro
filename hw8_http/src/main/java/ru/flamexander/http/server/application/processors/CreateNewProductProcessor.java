package ru.flamexander.http.server.application.processors;

import com.google.gson.Gson;
import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.application.Item;
import ru.flamexander.http.server.application.Storage;
import ru.flamexander.http.server.processors.AbstractProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewProductProcessor extends AbstractProcessor {

    private static final String CONTENT_TYPE_VALUE = "application/json";

    public CreateNewProductProcessor() {
        super(CONTENT_TYPE_VALUE);
    }

    @Override
    public void executeRequest(HttpRequest httpRequest, OutputStream output) {
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        Storage.save(item);
        String jsonOutItem = gson.toJson(item);

        String response = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: " + CONTENT_TYPE_VALUE + "\r\n" +
            "Connection: keep-alive\r\n" +
            "Access-Control-Allow-Origin: *" +
            addSessionIdForCookieIfNeed(httpRequest) + "\r\n" +
            "\r\n" + jsonOutItem;
        try {
            output.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
