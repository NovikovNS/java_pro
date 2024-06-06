package ru.flamexander.http.server.processors;

import ru.flamexander.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public abstract class AbstractProcessor implements RequestProcessor {
    private List<String> acceptHeaderValues;

    private static final String ACCEPT_HEADER = "Accept";
    private static final String COOKIE_HEADER = "Cookie";
    private static final String SESSION_ID_COOKIE_NAME = "SESSIONID";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";

    public AbstractProcessor(List<String> acceptHeaderValues) {
        this.acceptHeaderValues = acceptHeaderValues;
    }

    public void execute(HttpRequest httpRequest, OutputStream output, Boolean isCached) throws IOException {
        if (isAcceptHeadersNotSuitable(httpRequest)) {
            String response = "HTTP/1.1 406 Not Acceptable\r\n" +
                "Content-Type: text/html" +
                "\r\n\r\n<html><body><h1>Not Acceptable</h1></body></html>";;
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return;
        }
        executeRequest(httpRequest, output, isCached);
    }

    protected abstract void executeRequest(HttpRequest httpRequest, OutputStream output, Boolean isCached);

    protected String addSessionIdForCookieIfNeed(HttpRequest httpRequest) {
        if (!isSessionIdExist(httpRequest)) {
            return SET_COOKIE_HEADER + ": " + SESSION_ID_COOKIE_NAME + " = " + UUID.randomUUID() + ";";
        } else return "";
    }

    private Boolean isSessionIdExist(HttpRequest httpRequest) {
        String cookies = httpRequest.getHeaders().get(COOKIE_HEADER);
        return cookies != null && cookies.contains(SESSION_ID_COOKIE_NAME);
    }

    private Boolean isAcceptHeadersNotSuitable(HttpRequest httpRequest) {
        return !acceptHeaderValues.contains(httpRequest.getHeaders().get(ACCEPT_HEADER));
    }

}
