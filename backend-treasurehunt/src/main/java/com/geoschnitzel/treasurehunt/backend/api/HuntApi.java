package com.geoschnitzel.treasurehunt.backend.api;

import com.geoschnitzel.treasurehunt.rest.SHListItem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface HuntApi {

    @GetMapping("/getshlist")
    List<SHListItem> getSHList();
}