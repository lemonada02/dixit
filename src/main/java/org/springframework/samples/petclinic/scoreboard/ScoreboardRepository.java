package org.springframework.samples.petclinic.scoreboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreboardRepository extends CrudRepository<Scoreboard, Integer> {

    List<Scoreboard> findAll();

    @Query("SELECT s FROM Scoreboard s WHERE s.user.username = :username")
    List<Scoreboard> findByUsername(@Param("username") String username);
}