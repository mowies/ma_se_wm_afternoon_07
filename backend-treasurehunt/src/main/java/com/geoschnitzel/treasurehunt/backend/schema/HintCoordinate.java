package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.HintType;

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
        super(id, timeToUnlockHint, 100);
    }

    @Override
    public HintType getHintType() {
        return HintType.COORDINATE;
    }
}
