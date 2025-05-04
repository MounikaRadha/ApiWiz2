package com.RadhaMounika.ApiWiz.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class HelloController {
    @RequestMapping("/home")
    public Mono<String> hello() throws IOException {
        return Mono.just("hello worllld");
    }
}
