package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.model.GameRepository;
import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Game;
import com.geoschnitzel.treasurehunt.backend.schema.GameTarget;
import com.geoschnitzel.treasurehunt.backend.schema.Hint;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.schema.ItemFactory;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziPurchaseTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziUsedTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.Target;
import com.geoschnitzel.treasurehunt.backend.schema.User;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RestController
@RequestMapping("/api/user/{userID}/game")
public class GameService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private HuntRepository huntRepository;

    @RequestMapping(value = "/startGame/{huntID}", method = RequestMethod.GET)
    public GameItem startGame(@PathVariable long userID,@PathVariable long huntID) {
        User user = userRepository.findById(userID).get();
        if(user == null)
            return null;

        Hunt hunt = huntRepository.findById(huntID).get();
        if(hunt == null)
            return null;

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
        userRepository.save(user);
        return ItemFactory.CreateGameItem(game);
    }

    @GetMapping("/")
    public GameItem getGame(@PathVariable long userID) {
        User user = userRepository.findById(userID).get();
        if(user == null)
            return null;
        Game game = user.getCurrentGame();
        if(game == null)
            return null;

        return ItemFactory.CreateGameItem(game);
    }

    @GetMapping("/buyHint/{hintID}")
    public Boolean buyHint(@PathVariable long userID,@PathVariable long hintID)
    {
        User user = userRepository.findById(userID).get();
        if(user == null)
            return false;

        Game game = user.getCurrentGame();
        if(game == null)
            return false;

        GameTarget currentGameTarget = game.getTargets().get(game.getTargets().size()-1);
        Hunt hunt = game.getHunt();
        Target currentTarget = hunt.getTargets().get(game.getTargets().size()-1);
        Hint buyHint = null;
        for(Hint hint : currentTarget.getHints())
        {
            if(hint.getId() == hintID) {
                buyHint = hint;
                break;
            }
        }
        if(buyHint == null)
            return false;

        if(buyHint.getShValue() > user.getBalance())
            return false;

        user.getTransactions().add(new SchnitziUsedTransaction(null, new Date(),buyHint.getShValue(),
                buyHint.getHintType().getType() + "-Hint"));
        currentGameTarget.getUnlockedHints().add(buyHint);

        userRepository.save(user);
        gameRepository.save(game);

        return true;
    }
}
