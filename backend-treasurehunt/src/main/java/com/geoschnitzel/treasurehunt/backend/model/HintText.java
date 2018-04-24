package com.geoschnitzel.treasurehunt.backend.model;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class HintText extends Hint {

    private String description;
}
