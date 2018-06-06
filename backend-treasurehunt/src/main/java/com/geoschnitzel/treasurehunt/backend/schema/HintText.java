package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.HintType;

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

    public HintText(Long id, int timeToUnlockHint, int shValue, String description) {
        super(id, timeToUnlockHint, shValue);
        this.description = description;
    }

    @Override
    public HintType getHintType() {
        return HintType.TEXT;
    }
}
