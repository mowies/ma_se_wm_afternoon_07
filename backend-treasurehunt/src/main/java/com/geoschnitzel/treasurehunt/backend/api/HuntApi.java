package com.geoschnitzel.treasurehunt.backend.api;

import com.geoschnitzel.treasurehunt.rest.SHListItem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/hunt")
public interface HuntApi {

    @GetMapping("list")
    List<SHListItem> retrieveSchnitzelHunts();
}
