package com.geoschnitzel.treasurehunt.backend.model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class HintCoordinate extends Hint {
    public HintCoordinate() {
        super();
    }

    public HintCoordinate(Long id, int timeToUnlockHint) {
        super(id, timeToUnlockHint);
    }
}
