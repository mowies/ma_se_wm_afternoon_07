package com.geoschnitzel.treasurehunt.backend.schema;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {

    private double latitude;
    private double longitude;

}
