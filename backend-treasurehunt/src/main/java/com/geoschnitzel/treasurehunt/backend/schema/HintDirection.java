package com.geoschnitzel.treasurehunt.backend.schema;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class HintDirection extends Hint {
    public HintDirection() {
        super();
    }

    public HintDirection(Long id, int timeToUnlockHint) {
        super(id, timeToUnlockHint);
    }
}
