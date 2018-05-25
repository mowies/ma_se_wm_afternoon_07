package com.geoschnitzel.treasurehunt.backend.api;

import com.geoschnitzel.treasurehunt.backend.DateProvider;
import com.geoschnitzel.treasurehunt.rest.Message;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldApi {

    private final DateProvider dateProvider;

    public HelloWorldApi(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @GetMapping("helloWorld")
    public Message helloWorld() {
        return new Message("Hello World", dateProvider.currentDate());
    }

}
