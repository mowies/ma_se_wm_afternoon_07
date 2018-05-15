package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.model.GameRepository;
import com.geoschnitzel.treasurehunt.backend.model.SchnitzelHuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Game;
import com.geoschnitzel.treasurehunt.backend.schema.GameTarget;
import com.geoschnitzel.treasurehunt.backend.schema.Hint;
import com.geoschnitzel.treasurehunt.backend.schema.ItemFactory;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitzelHunt;
import com.geoschnitzel.treasurehunt.backend.schema.Target;
import com.geoschnitzel.treasurehunt.backend.schema.User;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RestController
public class DataService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private SchnitzelHuntRepository schnitzelHuntRepository;

    public GameItem startGame(long huntID, long userID) {
        User user = userRepository.findById(userID).get();
        SchnitzelHunt hunt = schnitzelHuntRepository.findById(huntID).get();
        Target firstTarget = hunt.getTargets().get(0);
        Hint firstHint = firstTarget.getHints().get(0);
        Game game = gameRepository.save(new Game(
                null,
                user,
                hunt,
                Arrays.asList(new GameTarget(null, firstTarget, new Date(), null, Arrays.asList(firstHint))),
                Collections.emptyList()
        ));
        user.setCurrentGame(game);
        return ItemFactory.CreateGameItem(game);
    }

    public List<HintItem> fetchHints(long userID) {
        User user = userRepository.findById(userID).get();
        if (user.getCurrentGame() != null) {
            return ItemFactory.CreateHintItem(user.getCurrentGame().getTargets().get(-1).getUnlockedHints(), true);
        }
        return Collections.emptyList();
    }
}
