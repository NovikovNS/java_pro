package ru.flamexander.http.server.processors;

import com.google.common.primitives.Bytes;
import ru.flamexander.http.server.HttpRequest;
import ru.flamexander.http.server.application.Cache;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class DefaultStaticResourcesProcessor extends AbstractProcessor {

    public DefaultStaticResourcesProcessor() {
        super(Collections.emptyList());
    }

    @Override
    public void executeRequest(HttpRequest httpRequest, OutputStream output, Boolean isCached) {
        String filename = httpRequest.getUri().substring(1);
        Path filePath = Paths.get("static/", filename);
        String fileType = filename.substring(filename.lastIndexOf(".") + 1);
        byte[] fileData = new byte[0];
        try {
            fileData = Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contentDisposition = "";
        if (fileType.equals("pdf")) {
            contentDisposition = "Content-Disposition: attachment;filename=" + filename + "\r\n";
        }

        String response = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: " + fileData.length + "\r\n" +
            addSessionIdForCookieIfNeed(httpRequest) + "\r\n" +
            contentDisposition +
                "\r\n";
        try {
            output.write(response.getBytes());
            output.write(fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isCached) {
            Cache.addCache(httpRequest.getUri(), Bytes.concat(response.getBytes(), fileData));
        }
    }
}
