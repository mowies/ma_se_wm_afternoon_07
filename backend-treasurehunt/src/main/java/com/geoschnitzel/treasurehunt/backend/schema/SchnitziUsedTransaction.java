package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.TransactionType;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SchnitziUsedTransaction extends SchnitziTransaction {

    /**
     * Placeholder until schnitzi usage system is in place
     */
    private String usedFor;

    public SchnitziUsedTransaction() {
        super();
    }

    public SchnitziUsedTransaction(Long id, Date time, int amount, String usedFor) {
        super(id, time, amount);
        this.usedFor = usedFor;
    }

    @Override
    public TransactionType getHintType() {
        return TransactionType.Used;
    }
}
