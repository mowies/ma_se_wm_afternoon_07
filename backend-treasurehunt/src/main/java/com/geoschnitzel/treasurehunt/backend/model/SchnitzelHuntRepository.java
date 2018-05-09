package com.geoschnitzel.treasurehunt.backend.model;

import com.geoschnitzel.treasurehunt.backend.schema.SchnitzelHunt;
import com.geoschnitzel.treasurehunt.backend.schema.User;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SchnitzelHuntRepository extends CrudRepository<SchnitzelHunt, Long> {

    List<SchnitzelHunt> findByCreator(User creator);
}
