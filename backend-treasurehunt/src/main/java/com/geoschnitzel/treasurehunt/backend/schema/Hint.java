package com.geoschnitzel.treasurehunt.backend.schema;


import com.geoschnitzel.treasurehunt.rest.HintType;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "HINT_TYPE")
@NoArgsConstructor
@AllArgsConstructor
public abstract class Hint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
        The time until the Hint gets unlocked without buying it
        in seconds
     */
    private int timeToUnlockHint;
    private int shValue;

    public abstract HintType getHintType();
    public String toString() {
        return "Type: '" + this.getHintType() + "', shValue: '" + this.shValue + "', timeToUnlockHint: '" + this.timeToUnlockHint + "'";
    }
}
