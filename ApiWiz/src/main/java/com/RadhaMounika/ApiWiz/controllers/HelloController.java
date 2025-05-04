package com.RadhaMounika.ApiWiz.controllers;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {
    @RequestMapping("/home")
    public Mono<String> hello(){
        return Mono.just("hello worllld");
    }
}
