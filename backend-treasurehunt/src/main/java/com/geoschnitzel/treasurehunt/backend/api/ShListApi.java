package com.geoschnitzel.treasurehunt.backend.api;

import com.geoschnitzel.treasurehunt.backend.model.SchnitzelHuntRepository;
import com.geoschnitzel.treasurehunt.backend.schema.SchnitzelHunt;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.util.UtilsKt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestMapping("/schnitzelHuntList")
@RestController
public class ShListApi {

    private SchnitzelHuntRepository schnitzelHuntRepository;

    public ShListApi(SchnitzelHuntRepository schnitzelHuntRepository) {
        this.schnitzelHuntRepository = schnitzelHuntRepository;
    }

    @GetMapping
    public List<SHListItem> retrieveSchnitzelHunts() {
        List<SchnitzelHunt> schnitzelHunts = UtilsKt.asList(schnitzelHuntRepository.findAll());
        return schnitzelHunts.stream().map(schnitzelHunt -> {
            return new SHListItem(
                    schnitzelHunt.getName(),
                    schnitzelHunt.getCreator().getDisplayName(),
                    -1.0f,
                    2.5f,
                    schnitzelHunt.getDescription(),
                    false
            ); //TODO calculate hardcoded values
        }).collect(toList());
    }

}
