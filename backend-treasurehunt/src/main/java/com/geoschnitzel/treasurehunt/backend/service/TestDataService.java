package com.geoschnitzel.treasurehunt.backend.service;


import com.geoschnitzel.treasurehunt.backend.api.TestDataApi;
import com.geoschnitzel.treasurehunt.backend.model.Area;
import com.geoschnitzel.treasurehunt.backend.model.HintText;
import com.geoschnitzel.treasurehunt.backend.model.SchnitzelHunt;
import com.geoschnitzel.treasurehunt.backend.model.Target;
import com.geoschnitzel.treasurehunt.backend.model.User;
import com.geoschnitzel.treasurehunt.backend.repository.SchnitzelHuntRepository;
import com.geoschnitzel.treasurehunt.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RestController
public class TestDataService implements TestDataApi {

    private SchnitzelHuntRepository schnitzelHuntRepository;
    private UserRepository userRepository;

    public TestDataService(SchnitzelHuntRepository schnitzelHuntRepository,
                           UserRepository userRepository) {
        this.schnitzelHuntRepository = schnitzelHuntRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void generateTestData() {

        List<User> users = getUserTestList();
        userRepository.saveAll(users);
        schnitzelHuntRepository.saveAll(getSchnitzelHuntTestList(users.get(1)));
    }

    public List<User> getUserTestList() {
        return Arrays.asList(new User(null, "User 1", "user1@schnitzel.com"),
                new User(null, "User 2", "user2@schnitzel.com"),
                new User(null, "User 3", "user3@schnitzel.com"),
                new User(null, "User 4", "user4@schnitzel.com"),
                new User(null, "User 5", "user5@schnitzel.com"));
    }

    public List<SchnitzelHunt> getSchnitzelHuntTestList(User user1) {
        return Arrays.asList(
                new SchnitzelHunt(
                        null,
                        "Schnitzelhunt 1",
                        "A hunt for a schnitzel 1",
                        10,
                        user1,
                        new Area(47.0748539, 15.4415758, 5),
                        Arrays.asList(
                                new Target(null, new Area(47.0748539, 15.4415758,
                                        5),
                                        Arrays.asList(
                                                new HintText()
                                        ))
                        )
                )
        );
    }

}
