package com.geoschnitzel.treasurehunt.backend.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    private double latitude;
    private double longitude;

    /**
     * Radius in meters.
     * Shouldn't be too small
     */
    private int radius;
}
