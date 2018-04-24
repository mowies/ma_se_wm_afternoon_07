package com.geoschnitzel.treasurehunt.backend.model;

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

    public HintImage(Long id, int timeToUnlockHint, String imageFileName, String mimeType) {
        super(id, timeToUnlockHint);
        this.imageFileName = imageFileName;
        this.mimeType = mimeType;
    }
}
