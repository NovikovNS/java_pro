package ru.flamexander.http.server.processors;

import ru.flamexander.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class AbstractProcessor implements RequestProcessor {
    private String acceptHeaderValue;

    private static final String ACCEPT_HEADER = "Accept";
    private static final String COOKIE_HEADER = "Cookie";
    private static final String SESSION_ID_COOKIE_NAME = "SESSIONID";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";

    public AbstractProcessor(String acceptHeaderValue) {
        this.acceptHeaderValue = acceptHeaderValue;
    }

    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        if (isAcceptHeadersNotSuitable(httpRequest)) {
            String response = "HTTP/1.1 406 Not Acceptable\r\n" +
                "Content-Type: text/html" +
                "\r\n\r\n<html><body><h1>Not Acceptable</h1></body></html>";;
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return;
        }
        executeRequest(httpRequest, output);
    }

    protected abstract void executeRequest(HttpRequest httpRequest, OutputStream output);

    protected String addSessionIdForCookieIfNeed(HttpRequest httpRequest) {
        if (!isSessionIdExist(httpRequest)) {
            return "\r\n" + SET_COOKIE_HEADER + ": " + SESSION_ID_COOKIE_NAME + " = " + UUID.randomUUID() + ";\r\n";
        } else return "\r\n";
    }

    private Boolean isSessionIdExist(HttpRequest httpRequest) {
        String cookies = httpRequest.getHeaders().get(COOKIE_HEADER);
        return cookies != null && cookies.contains(SESSION_ID_COOKIE_NAME);
    }

    private Boolean isAcceptHeadersNotSuitable(HttpRequest httpRequest) {
        if (acceptHeaderValue.equals("")) {
            return false;
        }
        List<String> requestAcceptHeaderValues = Arrays.asList(httpRequest.getHeaders().get(ACCEPT_HEADER)
            .replaceAll("\\s", "").split(","));
        return !requestAcceptHeaderValues.contains(acceptHeaderValue);
    }

}
