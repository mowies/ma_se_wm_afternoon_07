package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.model.GameRepository;
import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Game;
import com.geoschnitzel.treasurehunt.backend.schema.GameTarget;
import com.geoschnitzel.treasurehunt.backend.schema.Hint;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.schema.ItemFactory;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziUsedTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.Target;
import com.geoschnitzel.treasurehunt.backend.schema.User;
import com.geoschnitzel.treasurehunt.backend.schema.UserPosition;
import com.geoschnitzel.treasurehunt.backend.util.CalDistance;
import com.geoschnitzel.treasurehunt.rest.GameItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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

    @RequestMapping(value = "{gameID}/reachedTarget/", method = RequestMethod.GET)
    public boolean CheckTargetReached(@PathVariable long userID, @PathVariable long gameID)
    {
        if(!userRepository.findById(userID).isPresent())
            return false;
        User user = userRepository.findById(userID).get();
        if(!gameRepository.findById(gameID).isPresent())
            return false;
        Game game = gameRepository.findById(gameID).get();
        GameTarget cTarget = game.getTargets().get(game.getTargets().size() - 1);
        if(game.getUserPositions().size() == 0)
            return false;
        UserPosition position = game.getUserPositions().get(game.getUserPositions().size() - 1);
        if( CalDistance.distance(cTarget,position, CalDistance.ScaleType.Meter) < (double)cTarget.getTarget().getArea().getRadius())
        {
            GameTarget currentTarget = game.getTargets().get(game.getTargets().size() -1);
            currentTarget.setTimeReached(new Date());
            Target nextTarget = null;
            for (Target target :
                    game.getHunt().getTargets()) {
                boolean contains = false;
                for(GameTarget gameTarget : game.getTargets()) {
                    if(gameTarget.getTarget().getId()== target.getId()) {
                        contains = true;
                        break;
                    }
                }
                if (!contains)
                    nextTarget = target;
            }
            if(nextTarget != null) {
                List<Hint> hints = new ArrayList<>();
                hints.add(nextTarget.getHints().get(0));
                game.getTargets().add(new GameTarget(null,nextTarget,new Date(),null,hints));
            }
            else
            {
                game.setEnded(new Date());
            }
            gameRepository.save(game);

            return true;
        }
        return false;
    }
    @Transactional
    @RequestMapping(value = "/startGame/{huntID}", method = RequestMethod.GET)
    public GameItem startGame(@PathVariable long userID,@PathVariable long huntID) {
        if(!userRepository.findById(userID).isPresent()) {
            System.out.printf("The userID %d cannot be found\n", userID);
            return null;
        }
        User user = userRepository.findById(userID).get();
        if(!huntRepository.findById(huntID).isPresent()) {
            System.out.printf("The hunt %d cannot be found\n",huntID);
            return null;
        }
        Hunt hunt = huntRepository.findById(huntID).get();
        Target firstTarget = hunt.getTargets().get(0);
        List<Hint> firstHints = new ArrayList<>();
        firstHints.add(firstTarget.getHints().get(0));

        List<GameTarget> gameTargets = new ArrayList<>();
        gameTargets.add(new GameTarget(null, firstTarget, new Date(), null, firstHints));
        Game game = gameRepository.save(new Game(
                null,
                user,
                hunt,
                gameTargets,
                new ArrayList<>(),
                new Date(),
                null,
                null
        ));
        return ItemFactory.CreateGameItem(game);
    }

    @GetMapping("/{gameID}")
    public GameItem getGame(@PathVariable long userID,@PathVariable long gameID) {
        if(!userRepository.findById(userID).isPresent()) {
            System.out.printf("The userID %d cannot be found\n", userID);
            return null;
        }
        User user = userRepository.findById(userID).get();
        if(!gameRepository.findById(gameID).isPresent()) {
            System.out.printf("The game %d cannot be found\n",gameID);
            return null;
        }

        Game game = gameRepository.findById(gameID).get();
        if(game.getUser() != user) {
            System.out.printf("User {0} tried to access game {1}\n",userID,gameID);
            return null;
        }
        return ItemFactory.CreateGameItem(game);
    }

    @GetMapping("/{gameID}/buyHint/{hintID}")
    public Boolean buyHint(@PathVariable long userID,@PathVariable long gameID,@PathVariable long hintID)
    {
        if(!userRepository.findById(userID).isPresent()) {
            System.out.printf("The userID %d cannot be found\n", userID);
            return false;
        }
        User user = userRepository.findById(userID).get();

        if(!gameRepository.findById(gameID).isPresent()) {
            System.out.printf("The game %d cannot be found\n", gameID);
            return false;
        }

        Game game = gameRepository.findById(gameID).get();
        if(game.getUser() != user) {
            System.out.printf("User %d  tried to access game %d \n",userID,gameID);
            return false;
        }

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

        // Do not buy again
        if(currentGameTarget.getUnlockedHints().contains(buyHint))
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


    @GetMapping("/{gameID}/unlockHint/{hintID}")
    public Boolean unlockHint(@PathVariable long userID,@PathVariable long gameID,@PathVariable long hintID) {
        if(!userRepository.findById(userID).isPresent()) {
            System.out.printf("The userID %d cannot be found\n", userID);
            return false;
        }
        User user = userRepository.findById(userID).get();

        if(!gameRepository.findById(gameID).isPresent()) {
            System.out.printf("The game %d cannot be found\n", gameID);
            return false;
        }

        Game game = gameRepository.findById(gameID).get();
        if(game.getUser() != user) {
            System.out.printf("User %d  tried to access game {1}\n",userID,gameID);
            return false;
        }

        GameTarget currentGameTarget = game.getTargets().get(game.getTargets().size()-1);
        Hunt hunt = game.getHunt();
        Target currentTarget = hunt.getTargets().get(game.getTargets().size()-1);
        Hint unlockHint = null;
        for(Hint hint : currentTarget.getHints())
        {
            if(hint.getId() == hintID) {
                unlockHint = hint;
                break;
            }
        }
        if(unlockHint == null)
            return false;

        // Do not unlock again
        if(currentGameTarget.getUnlockedHints().contains(unlockHint))
            return false;

        if((currentGameTarget.getStartTime().getTime()  + unlockHint.getTimeToUnlockHint() * 1000) > new Date().getTime())
            return false;

        currentGameTarget.getUnlockedHints().add(unlockHint);

        gameRepository.save(game);

        return true;
    }
}
