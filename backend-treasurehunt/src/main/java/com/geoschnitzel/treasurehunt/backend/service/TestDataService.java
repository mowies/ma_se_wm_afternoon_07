package com.geoschnitzel.treasurehunt.backend.service;


import com.geoschnitzel.treasurehunt.backend.api.TestDataApi;
import com.geoschnitzel.treasurehunt.backend.model.Area;
import com.geoschnitzel.treasurehunt.backend.model.HintCoordinate;
import com.geoschnitzel.treasurehunt.backend.model.HintDirection;
import com.geoschnitzel.treasurehunt.backend.model.HintImage;
import com.geoschnitzel.treasurehunt.backend.model.HintText;
import com.geoschnitzel.treasurehunt.backend.model.SchnitzelHunt;
import com.geoschnitzel.treasurehunt.backend.model.Target;
import com.geoschnitzel.treasurehunt.backend.model.User;
import com.geoschnitzel.treasurehunt.backend.repository.SchnitzelHuntRepository;
import com.geoschnitzel.treasurehunt.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

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
                                                new HintText(null, 0, "Suche die höchste Uhr in Graz."),
                                                new HintText(null, 2 * 60, "Es ist eine analoge Uhr."),
                                                new HintImage(null, 5 * 60, "ccacb863-5897-485b-b822-ca119c7afcfb", "impage/jpeg"),
                                                new HintDirection(null, 10 * 60),
                                                new HintCoordinate(null, 15 * 60)
                                        ))
                        )
                ),
                new SchnitzelHunt(
                        null,
                        "Schnitzelhunt 2",
                        "A hunt for a schnitzel 2",
                        10,
                        user1,
                        new Area(47.0788539, 15.4418758, 5),
                        Arrays.asList(
                                new Target(null, new Area(47.0748539, 15.4415758,
                                        5),
                                        Arrays.asList(
                                                new HintText(null, 0, "Suche die höchste Uhr in Graz."),
                                                new HintText(null, 2 * 60, "Es ist eine analoge Uhr."),
                                                new HintImage(null, 5 * 60, "ccacb863-5897-485b-b822-ca119c7afcfb", "impage/jpeg"),
                                                new HintDirection(null, 10 * 60),
                                                new HintCoordinate(null, 15 * 60)
                                        ))
                        )
                ),
                new SchnitzelHunt(
                        null,
                        "Schnitzelhunt 3",
                        "A hunt for a schnitzel 3",
                        10,
                        user1,
                        new Area(47.0948539, 15.4419758, 5),
                        Arrays.asList(
                                new Target(null, new Area(47.0748539, 15.4415758,
                                        5),
                                        Arrays.asList(
                                                new HintText(null, 0, "Suche die höchste Uhr in Graz."),
                                                new HintText(null, 2 * 60, "Es ist eine analoge Uhr."),
                                                new HintImage(null, 5 * 60, "ccacb863-5897-485b-b822-ca119c7afcfb", "impage/jpeg"),
                                                new HintDirection(null, 10 * 60),
                                                new HintCoordinate(null, 15 * 60)
                                        ))
                        )
                ),
                new SchnitzelHunt(
                        null,
                        "Schnitzelhunt 4",
                        "A hunt for a schnitzel 4",
                        10,
                        user1,
                        new Area(47.0398539, 15.4454758, 5),
                        Arrays.asList(
                                new Target(null, new Area(47.0738539, 15.444758,
                                        5),
                                        Arrays.asList(
                                                new HintText(null, 0, "Suche die höchste Uhr in Graz."),
                                                new HintText(null, 2 * 60, "Es ist eine analoge Uhr."),
                                                new HintImage(null, 5 * 60, "ccacb863-5897-485b-b822-ca119c7afcfb", "impage/jpeg"),
                                                new HintDirection(null, 10 * 60),
                                                new HintCoordinate(null, 15 * 60)
                                        ))
                        )
                ),
                new SchnitzelHunt(
                        null,
                        "Schnitzelhunt 5",
                        "A hunt for a schnitzel 5",
                        10,
                        user1,
                        new Area(47.0743139, 15.4415128, 12),
                        Arrays.asList(
                                new Target(null, new Area(47.0748239, 15.4315758, 5),
                                        Arrays.asList(
                                                new HintText(null, 0, "Suche die höchste Uhr in Graz."),
                                                new HintText(null, 2 * 60, "Es ist eine analoge Uhr."),
                                                new HintImage(null, 5 * 60, "ccacb863-5897-485b-b822-ca119c7afcfb", "impage/jpeg"),
                                                new HintDirection(null, 10 * 60),
                                                new HintCoordinate(null, 15 * 60)
                                        ))
                        )
                )
        );
    }

}
