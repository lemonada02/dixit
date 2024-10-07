package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
    
    List<Game> findAll();

    @Query("SELECT g FROM Game g WHERE g.id = :id")
    Game findById(@Param("id") int id);

    @Query("SELECT r FROM Game g JOIN g.rounds r WHERE g.id = :gameId AND INDEX(r) = :roundNumber")
    Round findRoundByGameAndNumber(int gameId, int roundNumber);
}