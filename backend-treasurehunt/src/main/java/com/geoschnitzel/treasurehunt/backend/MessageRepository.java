package com.geoschnitzel.treasurehunt.backend;

import com.geoschnitzel.treasurehunt.backend.model.Message;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Message findMessageByMessage(String message);
}
