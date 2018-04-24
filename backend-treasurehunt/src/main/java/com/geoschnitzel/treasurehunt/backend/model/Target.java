package com.geoschnitzel.treasurehunt.backend.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * A single step in a treasure hunt
 */
@Entity
@Data
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Area area;

    @OneToMany
    private List<Hint> hints;

}
