package com.geoschnitzel.treasurehunt.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Service
@RestController
@RequestMapping("/api/time")
public class TimeService {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Long getCurrentTime()
    {
        return new Date().getTime();
    }
}
