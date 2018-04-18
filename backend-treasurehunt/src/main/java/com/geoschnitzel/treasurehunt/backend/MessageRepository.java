package com.geoschnitzel.treasurehunt.backend;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Message findMessageByMessage(String message);
}
