package com.geoschnitzel.treasurehunt.backend.service;


import com.geoschnitzel.treasurehunt.backend.api.TestDataApi;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class TestDataService implements TestDataApi {

    @Override
    public void generateTestData() {
        System.out.println("Working!");
    }
}
