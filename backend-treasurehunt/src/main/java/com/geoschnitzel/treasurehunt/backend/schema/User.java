package com.geoschnitzel.treasurehunt.backend.schema;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //TODO Google ID oder so

    private String displayName;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SchnitziTransaction> transactions;

    public int getBalance()
    {
        int balance = 0;
        for(SchnitziTransaction transaction : transactions)
        {
            switch (transaction.getHintType())
            {
                case Purchase:
                    balance += transaction.getAmount();
                    break;
                case Earned:
                    balance += transaction.getAmount();
                    break;
                case Used:
                    balance -= transaction.getAmount();
                    break;
            }
        }
        return balance;
    }
}
