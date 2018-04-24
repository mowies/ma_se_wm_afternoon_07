package com.geoschnitzel.treasurehunt.backend.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Area {

    private double longitude;
    private double latitude;

    /**
     * Radius in meters.
     * Shouldn't be too small
     */
    private int radius;
}
