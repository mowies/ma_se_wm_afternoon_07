package com.geoschnitzel.treasurehunt.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldApi {

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello World!";
    }

}
