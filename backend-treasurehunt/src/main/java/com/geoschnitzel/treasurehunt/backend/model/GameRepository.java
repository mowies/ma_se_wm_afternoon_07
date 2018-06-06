package com.geoschnitzel.treasurehunt.backend.model;

import com.geoschnitzel.treasurehunt.backend.schema.Game;
import com.geoschnitzel.treasurehunt.backend.schema.Hunt;
import com.geoschnitzel.treasurehunt.backend.schema.User;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    public List<Game> findByUser(User user);
    public List<Game> findByHuntAndUser(Hunt hunt, User user);
}
