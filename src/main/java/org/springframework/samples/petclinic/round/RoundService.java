package org.springframework.samples.petclinic.round;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoundService {
    
    protected RoundRepository roundRepository;

    RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    public List<Round> findAll() {
        return roundRepository.findAll();
    }

    public Round findById(int id) {
        return roundRepository.findById(id);
    }

    public void save(Round round) {
        roundRepository.save(round);
    }
}
