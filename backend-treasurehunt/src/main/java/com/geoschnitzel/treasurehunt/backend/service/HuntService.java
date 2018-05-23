package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.api.HuntApi;
import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.util.UtilsKt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
@RequestMapping("/api/hunt")
public class HuntService implements HuntApi {

    @Autowired
    private HuntRepository huntRepository;

    @Override
    public List<SHListItem> getSHList() {
        List<Hunt> actualHunts = UtilsKt.asList(huntRepository.findAll());
        List<SHListItem> results = new ArrayList<>();
        for (Hunt hunt : actualHunts) {
            results.add(new SHListItem(
                    hunt.getName(),
                    hunt.getCreator().getDisplayName(),
                    0,
                    (float) 4.5,
                    hunt.getDescription(),
                    false));

        }
        return results;
    }
}
