package ru.flamexander.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.flamexander.http.server.application.Cache;
import ru.flamexander.http.server.application.processors.*;
import ru.flamexander.http.server.processors.DefaultOptionsProcessor;
import ru.flamexander.http.server.processors.DefaultStaticResourcesProcessor;
import ru.flamexander.http.server.processors.DefaultUnknownOperationProcessor;
import ru.flamexander.http.server.processors.MethodNotAllowedProcessor;
import ru.flamexander.http.server.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private final Map<Map<String, HttpMethod>, RequestProcessor> router;
    private final RequestProcessor unknownOperationRequestProcessor;
    private final RequestProcessor optionsRequestProcessor;
    private final RequestProcessor staticResourcesProcessor;
    private final RequestProcessor methodNotAllowedProcessor;

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class.getName());

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put(Map.of("/calc", HttpMethod.GET), new CalculatorRequestProcessor());
        this.router.put(Map.of("/hello", HttpMethod.GET), new HelloWorldRequestProcessor());
        this.router.put(Map.of("/items", HttpMethod.GET), new GetAllProductsProcessor());
        this.router.put(Map.of("/items", HttpMethod.POST), new CreateNewProductProcessor());

        this.unknownOperationRequestProcessor = new DefaultUnknownOperationProcessor();
        this.optionsRequestProcessor = new DefaultOptionsProcessor();
        this.staticResourcesProcessor = new DefaultStaticResourcesProcessor();
        this.methodNotAllowedProcessor = new MethodNotAllowedProcessor();

        logger.info("Диспетчер проинициализирован");
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream) throws IOException {
        if (httpRequest.getMethod() == HttpMethod.OPTIONS) {
            optionsRequestProcessor.execute(httpRequest, outputStream, false);
            return;
        }
        if (Files.exists(Paths.get("static/", httpRequest.getUri().substring(1)))) {
            String uri = httpRequest.getUri();
            if (Cache.getCacheMap().containsKey(uri)) {
                outputStream.write(Cache.getCacheMap().get(uri));
                return;
            }
            staticResourcesProcessor.execute(httpRequest, outputStream, true);
            return;
        }

        if(router.keySet().stream().anyMatch(map -> map.containsKey(httpRequest.getUri()) && !map.containsValue(httpRequest.getMethod()))) {
            methodNotAllowedProcessor.execute(httpRequest, outputStream, true);
            return;
        }

        if (!router.containsKey(httpRequest.getRouteKey())) {
            unknownOperationRequestProcessor.execute(httpRequest, outputStream, false);
            return;
        }
        router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream, false);
    }
}
