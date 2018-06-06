package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.TransactionType;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SchnitziEarnedTransaction extends SchnitziTransaction {

    /**
     * Placeholder until schnitzi earning system is in place
     */
    private String earnedByDoing;

    public SchnitziEarnedTransaction() {
        super();
    }

    public SchnitziEarnedTransaction(Long id, Date time, int amount, String earnedByDoing) {
        super(id, time, amount);
        this.earnedByDoing = earnedByDoing;
    }

    @Override
    public TransactionType getHintType() {
        return TransactionType.Earned;
    }
}
