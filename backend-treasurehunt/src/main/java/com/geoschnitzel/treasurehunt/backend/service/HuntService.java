package com.geoschnitzel.treasurehunt.backend.service;

import com.geoschnitzel.treasurehunt.backend.api.HuntApi;
import com.geoschnitzel.treasurehunt.backend.model.HuntRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.geoschnitzel.treasurehunt.util.UtilsKt.asList;
import static java.util.stream.Collectors.toList;

@Service
@RestController
@RequestMapping("/api/hunt")
public class HuntService implements HuntApi {

    @Autowired
    private HuntRepository huntRepository;

    public HuntService(HuntRepository huntRepository) {
        this.huntRepository = huntRepository;
    }

    @Override
    public List<SHListItem> retrieveSchnitzelHunts() {
        List<Hunt> hunts = asList(huntRepository.findAll());
        return hunts.stream().map(hunt -> {
            return new SHListItem(
                    hunt.getId(),
                    hunt.getName(),
                    hunt.getCreator().getDisplayName(),
                    0,
                    (float) 4.5,
                    hunt.getDescription(),
                    false
            ); //TODO calculate values that are hardcoded now
        }).collect(toList());
    }
}
