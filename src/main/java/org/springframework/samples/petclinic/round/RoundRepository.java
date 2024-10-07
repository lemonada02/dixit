package org.springframework.samples.petclinic.round;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends CrudRepository<Round, Integer> {
    
    List<Round> findAll();

    @Query("SELECT r FROM Round r WHERE r.id = :id")
    Round findById(int id);
}
