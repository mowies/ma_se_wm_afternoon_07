package com.geoschnitzel.treasurehunt.backend.schema;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    public Message() {
    }

    public Message(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Message(String message) {
        this.message = message;
    }
}
