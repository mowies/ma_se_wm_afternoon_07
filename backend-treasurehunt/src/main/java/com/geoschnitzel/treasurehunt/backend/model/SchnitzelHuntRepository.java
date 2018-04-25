package com.geoschnitzel.treasurehunt.backend.model;

import com.geoschnitzel.treasurehunt.backend.schema.SchnitzelHunt;

import org.springframework.data.repository.CrudRepository;

public interface SchnitzelHuntRepository extends CrudRepository<SchnitzelHunt, Long> {
}
