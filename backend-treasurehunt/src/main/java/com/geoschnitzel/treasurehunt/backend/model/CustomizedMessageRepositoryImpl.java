package com.geoschnitzel.treasurehunt.backend.model;

import com.geoschnitzel.treasurehunt.backend.schema.Message;

import javax.persistence.EntityManager;

public class CustomizedMessageRepositoryImpl implements CustomizedMessageRepository {

    private final EntityManager entityManager;

    public CustomizedMessageRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Message storeAndReturnMessage(Message message) {
        entityManager.persist(message);

        return message;
    }
}
