package com.RadhaMounika.ApiWiz.factory;


import com.RadhaMounika.ApiWiz.dto.RequestDTO;
import com.RadhaMounika.ApiWiz.enums.ApiMethod;
import com.RadhaMounika.ApiWiz.util.ErrorHandler;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class AsyncRestFactory {

    private final HttpClient client;

    public AsyncRestFactory() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public AsyncRestFactory(SSLContext sslContext) {
        this.client = HttpClient.newBuilder()
                .sslContext(sslContext)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public CompletableFuture<HttpResponse<String>> executeAsync(ApiMethod method, RequestDTO requestDTO, int timeoutMs) throws Exception {
        HttpRequest request = generateRequest(method, requestDTO, timeoutMs);
        return sendAsyncRequestWithRetry(request, requestDTO);
    }

    private CompletableFuture<HttpResponse<String>> sendAsyncRequestWithRetry(HttpRequest request, RequestDTO requestDTO) throws Exception {
        if (requestDTO.isRetryEnabled()) {
            for (int i = 0; i < requestDTO.getRetryCount(); i++) {
                CompletableFuture<HttpResponse<String>> response = sendAsyncRequest(request);
                HttpResponse<String> res = response.get();
                if (!ErrorHandler.isErrored(res)) {
                    return response;
                }
            }
        }
        return sendAsyncRequest(request);
    }

    private CompletableFuture<HttpResponse<String>> sendAsyncRequest(HttpRequest request) {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            ErrorHandler.handleError(response);
            return response;
        });
    }

    private void addHeaders(HttpRequest.Builder builder, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(builder::header);
        }
    }

    private HttpRequest generateRequest(ApiMethod method, RequestDTO requestDTO, int timeoutMs) throws InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(requestDTO.getUrl()))
                .timeout(Duration.ofSeconds(timeoutMs));
        log.info("in thread {}", Thread.currentThread().getName());
        Thread.sleep(requestDTO.getSleepTime());
        addHeaders(requestBuilder, requestDTO.getHeaderVariables());

        switch (method) {
            case GET -> requestBuilder.GET();
            case DELETE -> requestBuilder.DELETE();
            case POST, PUT, PATCH -> {
                handleMethodsWithBodies(requestDTO, requestBuilder, method);
            }
            case OPTIONS -> requestBuilder.method("OPTIONS", HttpRequest.BodyPublishers.noBody());
            default -> throw new UnsupportedOperationException("Method not supported: " + method);
        }
        return requestBuilder.build();
    }

    private void handleMethodsWithBodies(RequestDTO requestDTO, HttpRequest.Builder requestBuilder, ApiMethod method) {
        String body = requestDTO.getRequestBody() != null ? requestDTO.getRequestBody() : "";
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body);
        switch (method) {
            case POST -> requestBuilder.POST(bodyPublisher);
            case PUT -> requestBuilder.PUT(bodyPublisher);
            case PATCH -> requestBuilder.method("PATCH", bodyPublisher);
        }
    }
}
