package com.geoschnitzel.treasurehunt.backend.api;

import org.springframework.web.bind.annotation.GetMapping;

public interface TestDataApi {

    @GetMapping("/generateTestData")
    void generateTestData();
}
