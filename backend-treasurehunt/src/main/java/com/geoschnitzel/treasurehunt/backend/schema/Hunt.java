package com.geoschnitzel.treasurehunt.backend.schema;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hunt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    /**
     * Maximum speed allowed in km/h
     */
    private int maxSpeed;

    @ManyToOne
    private User creator;

    @Embedded
    private Area startArea;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Target> targets;
}
