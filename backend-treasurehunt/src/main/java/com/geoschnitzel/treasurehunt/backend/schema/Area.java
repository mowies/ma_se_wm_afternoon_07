package com.geoschnitzel.treasurehunt.backend.schema;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    public Area(double latitude, double longitude, int radius) {
        this.coordinate = new Coordinate(latitude, longitude);
        this.radius = radius;
    }

    @Embedded
    private Coordinate coordinate;
    /**
     * Radius in meters.
     * Shouldn't be too small
     */
    private int radius;
}
