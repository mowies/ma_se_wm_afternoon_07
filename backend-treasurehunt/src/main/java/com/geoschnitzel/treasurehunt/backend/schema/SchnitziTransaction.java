package com.geoschnitzel.treasurehunt.backend.schema;

import com.geoschnitzel.treasurehunt.rest.HintType;
import com.geoschnitzel.treasurehunt.rest.TransactionType;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.spi.PersistenceUnitTransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TRANSACTION_TYPE")
@NoArgsConstructor
@AllArgsConstructor
public abstract class SchnitziTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date time;

    private int amount;

    public abstract TransactionType getHintType();

    public String toString()
    {
        return getHintType().getType() + " " + amount + " at " + time;
    }
}
