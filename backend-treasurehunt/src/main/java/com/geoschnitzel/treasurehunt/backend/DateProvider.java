package com.geoschnitzel.treasurehunt.backend;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateProvider {

    public Date currentDate() {
        return new Date();
    }

}
