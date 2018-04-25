package com.geoschnitzel.treasurehunt.backend.service;


import com.geoschnitzel.treasurehunt.backend.api.TestDataApi;
import com.geoschnitzel.treasurehunt.backend.model.GameRepository;
import com.geoschnitzel.treasurehunt.backend.model.SchnitzelHuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Area;
import com.geoschnitzel.treasurehunt.backend.schema.Game;
import com.geoschnitzel.treasurehunt.backend.schema.HintCoordinate;
import com.geoschnitzel.treasurehunt.backend.schema.HintDirection;
import com.geoschnitzel.treasurehunt.backend.schema.HintImage;
import com.geoschnitzel.treasurehunt.backend.schema.HintText;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitzelHunt;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziEarnedTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziUsedTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.Target;
import com.geoschnitzel.treasurehunt.backend.schema.User;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
@RestController
public class TestDataService implements TestDataApi {

    private final SchnitzelHuntRepository schnitzelHuntRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public TestDataService(SchnitzelHuntRepository schnitzelHuntRepository,
                           UserRepository userRepository, GameRepository gameRepository) {
        this.schnitzelHuntRepository = schnitzelHuntRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public void generateTestData() {
        List<User> users = generateUsers();
        userRepository.saveAll(users);
        List<SchnitzelHunt> schnitzelHunts = generateSchnitzelHunts(users.get(1));
        schnitzelHuntRepository.saveAll(schnitzelHunts);
        gameRepository.save(generateGame(users.get(0), schnitzelHunts.get(0)));
    }

    public Game generateGame(User user, SchnitzelHunt schnitzelHunt) {
        return new Game(null, user, schnitzelHunt, emptyList(), emptyList());
    }

    public List<SchnitzelHunt> generateSchnitzelHunts(User user) {
        return generateSchnitzelHunts(user, 5);
    }

    public List<User> generateUsers() {
        return generateUsers(5);
    }

    public List<User> generateUsers(int usersToGenerate) {
        List<User> generatedUsers = new ArrayList<>();
        for (int i = 0; i < usersToGenerate; i++) {
            generatedUsers.add(generateUser(i));
        }

        return generatedUsers;
    }

    private User generateUser(int userId) {
        return new User(null, "User " + userId, "user" + userId + "@schnitzel.com", generateUserTransactions(userId));
    }

    private List<SchnitziTransaction> generateUserTransactions(int seed) {
        return Arrays.asList(
                new SchnitziEarnedTransaction(null, new Date(1524660129 + seed + 1), 5 + seed, "By testing"),
                new SchnitziUsedTransaction(null, new Date(1524660129 + seed + 1000), 4 + seed, "For testing")
        );
    }

    public List<SchnitzelHunt> generateSchnitzelHunts(User user, int schnitzelHuntsToGenerate) {
        List<SchnitzelHunt> schnitzelHunts = new ArrayList<>();

        for (int i = 0; i < schnitzelHuntsToGenerate; i++) {
            schnitzelHunts.add(new SchnitzelHunt(
                            null,
                            "Schnitzelhunt " + i,
                            "A hunt for a schnitzel " + i,
                            10 + i,
                            user,
                            new Area(47.0748539 + i * 0.001, 15.4415758 - i * 0.001, 5),
                            singletonList(
                                    new Target(null, new Area(47.0748539 + i * 0.001, 15.4415758 - i * 0.001, 5),
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

        return schnitzelHunts;
    }

}
