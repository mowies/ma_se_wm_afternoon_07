package com.geoschnitzel.treasurehunt.backend.repository;

import com.geoschnitzel.treasurehunt.backend.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
