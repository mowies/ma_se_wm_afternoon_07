package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.model.UserRepository;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitziEarnedTransaction;
import com.geoschnitzel.treasurehunt.backend.schema.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static java.util.Arrays.asList;

@Service
@RestController
@RequestMapping("/api/user")
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public Long login()
    {
        User user = userRepository.save(new User(null,"Guest User","Guest@example.com",asList(new SchnitziEarnedTransaction(null,new Date(),100,"Registered")),null));
        return user.getId();
    }
}
