package com.geoschnitzel.treasurehunt.backend;

import org.springframework.web.bind.annotation.GetMapping;

public interface TestDataController {

    @GetMapping("/generateTestData")
    void generateTestData();
}
