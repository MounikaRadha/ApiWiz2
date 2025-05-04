package com.RadhaMounika.ApiWiz.controllers;


import com.RadhaMounika.ApiWiz.dto.RequestDTO;
import com.RadhaMounika.ApiWiz.enums.ApiMethod;
import com.RadhaMounika.ApiWiz.factory.RestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @RequestMapping("/home")
    public Mono<String> hello() throws IOException {
        RestFactory ff = new RestFactory();
        Map<String,String> dummyHeaders = new HashMap<>();
        RequestDTO dto = new RequestDTO();
        dto.setUrl("https://webhook.site/a5a948ac-d5ac-4677-9ba2-d527786f349b");
        dto.setHeaderVariables(dummyHeaders);
        dummyHeaders.put("Content-Type","application/json");
        ff.executeTarget(ApiMethod.GET,
               dto,null,4000);
        return Mono.just("hello worllld");
    }
}
