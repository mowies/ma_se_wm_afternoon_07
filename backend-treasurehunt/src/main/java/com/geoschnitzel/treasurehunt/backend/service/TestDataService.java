package com.geoschnitzel.treasurehunt.backend.service;


import com.geoschnitzel.treasurehunt.backend.model.GameRepository;
import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Area;
import com.geoschnitzel.treasurehunt.backend.schema.Game;
import com.geoschnitzel.treasurehunt.backend.schema.HintCoordinate;
import com.geoschnitzel.treasurehunt.backend.schema.HintDirection;
import com.geoschnitzel.treasurehunt.backend.schema.HintImage;
import com.geoschnitzel.treasurehunt.backend.schema.HintText;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziEarnedTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziUsedTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.Target;
import com.geoschnitzel.treasurehunt.backend.schema.User;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
public class TestDataService {

    private final HuntRepository huntRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public TestDataService(HuntRepository huntRepository,
                           UserRepository userRepository, GameRepository gameRepository) {
        this.huntRepository = huntRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void generateTestData() {
        if (userRepository.count() >= 0) {
            return;
        }

        List<User> users = generateUsers();
        userRepository.saveAll(users);
        List<Hunt> hunts = generateSchnitzelHunts(users.get(1));
        huntRepository.saveAll(hunts);
        gameRepository.save(generateGame(users.get(0), hunts.get(0)));
    }

    public Game generateGame(User user, Hunt hunt) {
        return new Game(null, user, hunt, emptyList(), emptyList());
    }

    public List<Hunt> generateSchnitzelHunts(User user) {
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

    public List<Hunt> generateSchnitzelHunts(User user, int schnitzelHuntsToGenerate) {
        List<Hunt> hunts = new ArrayList<>();

        for (int i = 0; i < schnitzelHuntsToGenerate; i++) {
            hunts.add(new Hunt(
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

        return hunts;
    }

}
