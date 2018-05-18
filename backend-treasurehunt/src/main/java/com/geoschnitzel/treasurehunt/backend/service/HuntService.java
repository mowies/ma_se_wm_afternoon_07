package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.api.HuntApi;
import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.util.UtilsKt;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
