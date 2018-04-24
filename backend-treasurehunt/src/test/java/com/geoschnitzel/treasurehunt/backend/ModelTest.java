package com.geoschnitzel.treasurehunt.backend;

import com.geoschnitzel.treasurehunt.backend.model.SchnitzelHunt;
import com.geoschnitzel.treasurehunt.backend.repository.SchnitzelHuntRepository;
import com.geoschnitzel.treasurehunt.backend.service.TestDataService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.geoschnitzel.treasurehunt.util.UtilsKt.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelTest {

    @Autowired
    private TestDataService testDataService;

    @Autowired
    private SchnitzelHuntRepository schnitzelHuntRepository;

    @Before
    public void generateTestData() {
        testDataService.generateTestData();
    }

    @Test
    public void testDataExists() {
        List<SchnitzelHunt> schnitzelHunts = asList(schnitzelHuntRepository.findAll());
        assertThat(schnitzelHunts, hasSize(1));
    }
}
