package com.geoschnitzel.treasurehunt.backend.service;


import com.geoschnitzel.treasurehunt.backend.api.TestDataApi;
import com.geoschnitzel.treasurehunt.backend.model.Area;
import com.geoschnitzel.treasurehunt.backend.model.SchnitzelHunt;
import com.geoschnitzel.treasurehunt.backend.model.User;
import com.geoschnitzel.treasurehunt.backend.repository.SchnitzelHuntRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class TestDataService implements TestDataApi {

    private SchnitzelHuntRepository schnitzelHuntRepository;

    public TestDataService(SchnitzelHuntRepository schnitzelHuntRepository) {
        this.schnitzelHuntRepository = schnitzelHuntRepository;
    }

    @Override
    public void generateTestData() {
        schnitzelHuntRepository.save(
                new SchnitzelHunt(
                        null,
                        "Schnitzelhunt 1",
                        "A hunt for a schnitzel 1",
                        10,
                        new User(null, "User 1", "user1@schnitzel.com"),
                        new Area(47.0748539, 15.4415758, 5))
        );
    }

}
