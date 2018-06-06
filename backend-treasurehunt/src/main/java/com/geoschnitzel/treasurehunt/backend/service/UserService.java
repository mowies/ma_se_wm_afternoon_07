package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.model.GameRepository;
import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Coordinate;
import com.geoschnitzel.treasurehunt.backend.schema.Game;
import com.geoschnitzel.treasurehunt.backend.schema.ItemFactory;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziEarnedTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.User;
import com.geoschnitzel.treasurehunt.backend.schema.UserPosition;
import com.geoschnitzel.treasurehunt.rest.CoordinateItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@RestController
@RequestMapping("/api/user")
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/login")
    public UserItem login()
    {
        List<SchnitziTransaction> transactions = new ArrayList<>();
        transactions.add(new SchnitziEarnedTransaction(null,new Date(),100,"Registered"));
        User user = userRepository.save(new User(null,"Guest User","Guest@example.com",transactions));
        if(user == null)
            return null;
        return ItemFactory.CreateUserItem(user);
    }
    @GetMapping("/{userID}")
    public UserItem getUser(@PathVariable long userID) {
        if(!userRepository.findById(userID).isPresent()) {
            System.out.printf("The userID %d cannot be found\n", userID);
            return null;
        }
        User user = userRepository.findById(userID).get();
        return ItemFactory.CreateUserItem(user);
    }
    @PostMapping("/{userID}/game/{gameID}/location/")
    public CoordinateItem pushUserLocation(@PathVariable long userID,@PathVariable long gameID,@RequestBody CoordinateItem location) {
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
        Coordinate coordinate = new Coordinate(location.getLatitude(),location.getLongitude());
        game.getUserPositions().add(new UserPosition(null,coordinate,new Date()));
        gameRepository.save(game);
        return location;
    }

}
