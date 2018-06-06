package com.geoschnitzel.treasurehunt.backend.service;


import com.geoschnitzel.treasurehunt.backend.model.GameRepository;
import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Area;
import com.geoschnitzel.treasurehunt.backend.schema.Coordinate;
import com.geoschnitzel.treasurehunt.backend.schema.Game;
import com.geoschnitzel.treasurehunt.backend.schema.GameTarget;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
public class TestDataService {

    @Autowired
    private HuntRepository huntRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void generateTestData() {
        if (userRepository.count() > 0) {
            return;
        }

        List<User> users = generateUsers();
        userRepository.saveAll(users);
        List<Hunt> hunts = generateSchnitzelHunts(users.get(1));
        huntRepository.saveAll(hunts);
        gameRepository.save(generateGame(users.get(0), hunts.get(0)));
    }

    public Game generateGame(User user, Hunt hunt) {
        return new Game(null, user, hunt, generateGameTarget(hunt.getTargets()), emptyList(), new Date(), null, null);
    }

    public List<GameTarget> generateGameTarget(List<Target> targets) {
        List<GameTarget> results = new ArrayList<>();
        for (Target target : targets) {
            results.add(new GameTarget(null, target, new Date(), null, emptyList()));
        }
        return results;

    }

    public List<Hunt> generateSchnitzelHunts(User user) {
        List<Hunt> hunts = new ArrayList<>(generateSchnitzelHunts(user, 5));
        hunts.add(generatePlayableHunt(user));
        return hunts;
    }

    private Area area(double latitude, double longitude) {
        Area area = new Area();

        area.setCoordinate(new Coordinate(latitude, longitude));
        area.setRadius(12);

        return area;
    }

    private Hunt generatePlayableHunt(User user) {
        Area startArea = area(47.06410917, 15.45092822);

        List<Target> targets = new ArrayList<>();

        Target middleTarget = new Target();
        middleTarget.setArea(area(47.06552446, 15.45193539));
        middleTarget.setHints(singletonList(new HintText(null, 0, 0, "It's not East & West")));
        targets.add(middleTarget);

        Target endTarget = new Target();
        endTarget.setArea(area(47.06470797, 15.45007931));
        endTarget.setHints(singletonList(new HintText(null, 0, 0, "Doener & Pizza")));
        targets.add(endTarget);

        Hunt hunt = new Hunt();

        hunt.setCreator(user);
        hunt.setDescription("Actually playable hunt");
        hunt.setMaxSpeed(25); //riding a bike is okay
        hunt.setName("Playable hunt");
        hunt.setStartArea(startArea);
        hunt.setTargets(targets);

        return hunt;
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
        List<SchnitziTransaction> result = new ArrayList<>();
        result.add(new SchnitziEarnedTransaction(null, new Date(1524660129 + seed + 1), 5 + seed, "By testing"));
        result.add(new SchnitziUsedTransaction(null, new Date(1524660129 + seed + 1000), 4 + seed, "For testing"));
        return result;
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
                                                    new HintText(null, 0, 0, "Suche die höchste Uhr in Graz."),
                                                    new HintText(null, 2, 10, "Es ist eine analoge Uhr."),
                                                    new HintImage(null, 2 * 60, 20, "ccacb863-5897-485b-b822-ca119c7afcfb", "impage/jpeg"),
                                                    new HintDirection(null, 5 * 60),
                                                    new HintCoordinate(null, 10 * 60)
                                            ))
                            )
                    )
            );
        }

        return hunts;
    }

}
