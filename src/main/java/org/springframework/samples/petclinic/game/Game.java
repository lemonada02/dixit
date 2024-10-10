package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.scoreboard.Scoreboard;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity {
    
    @NotNull
    private Integer numberOfPlayers;

    @NotNull
    private Integer numberOfRounds;

    @NotNull
    private String creator;

    @OneToMany
    private List<Scoreboard> scoreboards;

    @OneToMany
    private List<Card> cards;

    @OneToMany
    private List<Round> rounds;
}