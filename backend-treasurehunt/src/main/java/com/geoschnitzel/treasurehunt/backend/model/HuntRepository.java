package com.geoschnitzel.treasurehunt.backend.model;

import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.schema.User;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HuntRepository extends CrudRepository<Hunt, Long> {

    List<Hunt> findByCreator(User creator);
}
