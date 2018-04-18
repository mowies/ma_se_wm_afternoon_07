package com.geoschnitzel.treasurehunt.backend;

import com.geoschnitzel.treasurehunt.rest.Message;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private final DateProvider dateProvider;

    public HelloWorldController(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @GetMapping("/helloWorld")
    public Message helloWorld() {
        return new Message("Hello World", dateProvider.currentDate());
    }

}
