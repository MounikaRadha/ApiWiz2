package com.RadhaMounika.ApiWiz.factory;


import com.RadhaMounika.ApiWiz.dto.RequestDTO;
import com.RadhaMounika.ApiWiz.enums.ApiMethod;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
@Component
public class AsyncRestFactory {

    private final HttpClient client;

    public AsyncRestFactory() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }
    public AsyncRestFactory(SSLContext sslContext) {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .sslContext(sslContext)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public CompletableFuture<HttpResponse<String>> executeAsync(ApiMethod method, RequestDTO requestDTO, int timeoutMs) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(requestDTO.getUrl()))
                .timeout(Duration.ofMillis(timeoutMs));

        addHeaders(requestBuilder, requestDTO.getHeaderVariables());

        switch (method) {
            case GET -> requestBuilder.GET();
            case DELETE -> requestBuilder.DELETE();
            case POST, PUT, PATCH -> {
                String body = requestDTO.getRequestBody() != null ? requestDTO.getRequestBody() : "";
                HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body);
                switch (method) {
                    case POST -> requestBuilder.POST(bodyPublisher);
                    case PUT -> requestBuilder.PUT(bodyPublisher);
                    case PATCH -> requestBuilder.method("PATCH", bodyPublisher);
                }
            }
            case OPTIONS -> requestBuilder.method("OPTIONS", HttpRequest.BodyPublishers.noBody());
            default -> throw new UnsupportedOperationException("Method not supported: " + method);
        }

        HttpRequest request = requestBuilder.build();
        client.connectTimeout();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    private void addHeaders(HttpRequest.Builder builder, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(builder::header);
        }
    }
}
