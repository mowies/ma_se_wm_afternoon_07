package com.geoschnitzel.treasurehunt.backend.schema;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class HintText extends Hint {

    private String description;

    public HintText() {
        super();
    }

    public HintText(Long id, int timeToUnlockHint, String description) {
        super(id, timeToUnlockHint);
        this.description = description;
    }
}
