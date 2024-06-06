package ru.flamexander.http.server.application.processors;

import com.google.gson.Gson;
import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.application.Item;
import ru.flamexander.http.server.application.Storage;
import ru.flamexander.http.server.processors.AbstractProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetAllProductsProcessor extends AbstractProcessor {
    private static final String CONTENT_TYPE_VALUE = "application/json";

    public GetAllProductsProcessor() {
        super(List.of(CONTENT_TYPE_VALUE));
    }

    @Override
    public void executeRequest(HttpRequest httpRequest, OutputStream output, Boolean isCached) {
        List<Item> items = Storage.getItems();
        Gson gson = new Gson();
        String result = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: application/json\r\n" +
            "Connection: keep-alive\r\n" +
            "Access-Control-Allow-Origin: *" + "\r\n" +
            addSessionIdForCookieIfNeed(httpRequest) + "\r\n\r\n" +
            gson.toJson(items);
        try {
            output.write(result.getBytes(StandardCharsets.UTF_8));
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
