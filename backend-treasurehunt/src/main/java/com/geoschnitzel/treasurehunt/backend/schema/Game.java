package com.geoschnitzel.treasurehunt.backend.schema;

import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Hunt hunt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameTarget> targets;

    @OneToMany
    private List<UserPosition> userPositions;

    private Date started;

    @Nullable
    private Date ended;

    @Nullable
    private Date paused;

}
