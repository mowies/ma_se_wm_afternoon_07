package com.geoschnitzel.treasurehunt.backend.model;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class HintImage extends Hint {

    private String imageFileName;

    private String mimeType;

}
