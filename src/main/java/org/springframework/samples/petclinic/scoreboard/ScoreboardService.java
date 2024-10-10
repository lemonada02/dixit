package org.springframework.samples.petclinic.scoreboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreboardService {

    ScoreboardRepository scoreboardRepository;

    @Autowired
    ScoreboardService(ScoreboardRepository scoreboardRepository) {
        this.scoreboardRepository = scoreboardRepository;
    }

    List<Scoreboard> findAll() {
        return scoreboardRepository.findAll();
    }
    
    public List<Scoreboard> findByUsername(String username) {
        return scoreboardRepository.findByUsername(username);
    }

    public Scoreboard findById(Integer id) {
        return scoreboardRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        scoreboardRepository.deleteById(id);
    }

    public void save(Scoreboard scoreboard) {
        scoreboardRepository.save(scoreboard);
    }
}
