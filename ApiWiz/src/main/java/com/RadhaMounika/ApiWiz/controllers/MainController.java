package com.RadhaMounika.ApiWiz.controllers;

import com.RadhaMounika.ApiWiz.dto.RequestDTO;
import com.RadhaMounika.ApiWiz.enums.ApiMethod;
import com.RadhaMounika.ApiWiz.factory.AsyncRestFactory;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@RestController("/")
public class MainController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/index")
    public CompletableFuture<HttpResponse<String>> index(
            @RequestParam("apiMethod") String apiMethod,
            @RequestParam("timeoutMS") int timeoutMs,
            @RequestBody RequestDTO requestDTO
    ) throws Exception {
        AsyncRestFactory asyncRestFactory = new AsyncRestFactory();
        return asyncRestFactory.executeAsync(ApiMethod.valueOf(apiMethod), requestDTO, timeoutMs);
    }
}
