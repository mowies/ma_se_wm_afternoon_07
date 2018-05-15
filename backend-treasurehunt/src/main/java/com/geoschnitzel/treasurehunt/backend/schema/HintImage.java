package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.HintType;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class HintImage extends Hint {

    private String imageFileName;

    private String mimeType;

    public HintImage() {
        super();
    }

    public HintImage(Long id, int timeToUnlockHint, int shValue, String imageFileName, String mimeType) {
        super(id, timeToUnlockHint, shValue);
        this.imageFileName = imageFileName;
        this.mimeType = mimeType;
    }

    @Override
    public HintType getHintType() {
        return HintType.IMAGE;
    }
}
