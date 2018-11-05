package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.schema.ItemFactory;
import com.geoschnitzel.treasurehunt.rest.HuntItem;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.geoschnitzel.treasurehunt.util.UtilsKt.asList;
import static java.util.stream.Collectors.toList;

@Service
@RestController
@RequestMapping("/api/hunt")
public class HuntService {

    @Autowired
    private HuntRepository huntRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<SHListItem> retrieveSchnitzelHunts() {
        List<Hunt> hunts = asList(huntRepository.findAll());
        return hunts.stream().map(hunt -> {
            return ItemFactory.CreateSHListItem(hunt);
        }).collect(toList());
    }

    @RequestMapping(value = "/{huntID}", method = RequestMethod.GET)
    public HuntItem retrieveSchnitzelHunt(@PathVariable long huntID) {
        if(!huntRepository.findById(huntID).isPresent())
            return null;
        Hunt hunt = huntRepository.findById(huntID).get();
        return ItemFactory.CreateHuntItem(hunt);
    }
}
