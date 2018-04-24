package com.geoschnitzel.treasurehunt.backend;

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
    private TestDataGenerator testDataGenerator;

    @Before
    public void generateTestData() {
        testDataGenerator.generateTestData();
    }

    @Test
    public void testDataExists() {
        //nothing
    }
}
