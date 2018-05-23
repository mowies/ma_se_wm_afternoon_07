package com.geoschnitzel.treasurehunt.backend.api;

import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.geoschnitzel.treasurehunt.util.UtilsKt.asList;
import static java.util.stream.Collectors.toList;

@RequestMapping("/schnitzelHuntList")
@RestController
public class ShListApi {

    private HuntRepository huntRepository;

    public ShListApi(HuntRepository huntRepository) {
        this.huntRepository = huntRepository;
    }

    @GetMapping
    public List<SHListItem> retrieveSchnitzelHunts() {
        List<Hunt> hunts = asList(huntRepository.findAll());
        return hunts.stream().map(hunt -> {
            return new SHListItem(
                    hunt.getName(),
                    hunt.getCreator().getDisplayName(),
                    -1.0f,
                    2.5f,
                    hunt.getDescription(),
                    false
            ); //TODO calculate values that are hardcoded now
        }).collect(toList());
    }

}
