package com.geoschnitzel.treasurehunt.backend.model;

import com.geoschnitzel.treasurehunt.backend.schema.Message;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long>, CustomizedMessageRepository {

    Message findMessageByMessage(String message);
}
