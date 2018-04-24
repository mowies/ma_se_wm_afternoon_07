package com.geoschnitzel.treasurehunt.backend.model;


import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "HINT_TYPE")
public abstract class Hint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private int timeToUnlockHint;

}
