package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.TransactionType;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SchnitziPurchaseTransaction extends SchnitziTransaction {

    /**
     * Placeholder until we know better
     */
    private Long googlePlayTransactionId;

    public SchnitziPurchaseTransaction() {
        super();
    }

    public SchnitziPurchaseTransaction(Long id, Date time, int amount, Long googlePlayTransactionId) {
        super(id, time, amount);
        this.googlePlayTransactionId = googlePlayTransactionId;
    }

    @Override
    public TransactionType getHintType() {
        return TransactionType.Purchase;
    }
}
