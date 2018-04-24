package com.geoschnitzel.treasurehunt.backend;

import com.geoschnitzel.treasurehunt.backend.service.TestDataService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelTest {

    @Autowired
    private TestDataService testDataService;

    @Before
    public void generateTestData() {
        testDataService.generateTestData();
    }

    @Test
    public void testDataExists() {
        //nothing
    }
}
