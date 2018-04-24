package com.geoschnitzel.treasurehunt.backend.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchnitzelHunt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    /**
     * Maximum speed allowed in km/h
     */
    private int maxSpeed;

    @ManyToOne
    private User creator;

    @Embedded
    private Area startArea;


}
