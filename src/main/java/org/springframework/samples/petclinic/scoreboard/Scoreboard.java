package org.springframework.samples.petclinic.scoreboard;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "scoreboards")
public class Scoreboard extends BaseEntity{

    @NotNull
    private Integer order;

    @NotNull
    @PositiveOrZero
    private Integer score;

    @OneToOne
    private User user;
}