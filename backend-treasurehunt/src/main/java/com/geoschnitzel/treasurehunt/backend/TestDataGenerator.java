package com.geoschnitzel.treasurehunt.backend;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class TestDataGenerator implements TestDataController {

    @Override
    public void generateTestData() {
        System.out.println("Working!");
    }
}
