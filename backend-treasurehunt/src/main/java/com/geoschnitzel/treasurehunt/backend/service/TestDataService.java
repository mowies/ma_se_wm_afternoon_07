package com.geoschnitzel.treasurehunt.backend.service;


import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Area;
import com.geoschnitzel.treasurehunt.backend.schema.Coordinate;
import com.geoschnitzel.treasurehunt.backend.schema.Hint;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Service
@RestController
@RequestMapping("/api/test")
public class TestDataService {
    public static boolean isTestDataGenerated = false;
    @Autowired
    private HuntRepository huntRepository;
    @Autowired
    private UserRepository userRepository;


    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    @RequestMapping(value = "/generatetestdata", method = RequestMethod.GET)
    public void generateTestData() {
        if (isTestDataGenerated) return;

        List<User> users = generateUsers();
        userRepository.saveAll(users);
        List<Hunt> hunts = generateSchnitzelHunts(users.get(1));
        huntRepository.saveAll(hunts);

        isTestDataGenerated = true;
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
        List<Hint> hints = new ArrayList<>();
        hints.add(new HintText(null, 0, 0, "It's not East & West"));
        hints.add(new HintText(null, 30, 10, "You can eat there"));
        hints.add(new HintImage(null, 2*60, 25,"4ae5b13e-ea41-4b37-b082-aebdf4919db5","png"));
        middleTarget.setHints(hints);
        targets.add(middleTarget);

        Target endTarget = new Target();
        endTarget.setArea(area(47.064085, 15.449850));
        List<Hint> hints2 = new ArrayList<>();
        hints2.add(new HintText(null, 0, 0, "Almost a drive through restaurant."));
        hints2.add(new HintText(null, 10, 30, "Its close to where you are and you will go there if you have no bike."));
        hints2.add(new HintText(null, 25, 60, "Come on! It's a restaurant near you by the bim station."));
        endTarget.setHints(hints2);
        targets.add(endTarget);

        Hunt hunt = new Hunt();

        hunt.setCreator(user);
        hunt.setDescription("We will guide you through the most popular restaurants around the 'Neue Technik'");
        hunt.setMaxSpeed(25); //riding a bike is okay
        hunt.setName("Restaurant Tour (Neue Technik)");
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
                            asList(
                                    new Target(null, new Area(47.0748539 + i * 0.001, 15.4415758 - i * 0.001, 100),
                                            asList(
                                                    new HintText(null, 0, 0, "Suche die höchste Uhr in Graz."),
                                                    new HintText(null, 2, 10, "Es ist eine analoge Uhr."),
                                                    new HintImage(null, 2 * 60, 20, "ccacb863-5897-485b-b822-ca119c7afcfb", "impage/jpeg"),
                                                    new HintDirection(null, 5 * 60),
                                                    new HintCoordinate(null, 10 * 60)
                                            )),
                                    new Target(null, new Area(12.0748539 + i * 0.001, 20.4415758 - i * 0.001, 100),
                                            asList(
                                                    new HintText(null, 0, 0, "Es ist etwas weiter weg"),
                                                    new HintText(null, 2, 10, "Keine Uhr mehr"),
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
