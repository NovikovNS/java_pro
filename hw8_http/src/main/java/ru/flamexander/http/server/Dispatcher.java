package ru.flamexander.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.flamexander.http.server.application.processors.CalculatorRequestProcessor;
import ru.flamexander.http.server.application.processors.CreateNewProductProcessor;
import ru.flamexander.http.server.application.processors.GetAllProductsProcessor;
import ru.flamexander.http.server.application.processors.HelloWorldRequestProcessor;
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
            optionsRequestProcessor.execute(httpRequest, outputStream);
            return;
        }
        if (Files.exists(Paths.get("static/", httpRequest.getUri().substring(1)))) {
            staticResourcesProcessor.execute(httpRequest, outputStream);
            return;
        }

        if (!router.containsKey(httpRequest.getRouteKey())) {
            unknownOperationRequestProcessor.execute(httpRequest, outputStream);
            return;
        }

        if (router.keySet().stream().noneMatch(map -> map.containsKey(httpRequest.getUri()) && map.containsValue(httpRequest.getMethod()))) {
            methodNotAllowedProcessor.execute(httpRequest, outputStream);
            return;
        }

        router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream);
    }
}
