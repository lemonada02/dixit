package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.scoreboard.ScoreboardService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    protected GameRepository gameRepository;

    @Autowired
    GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    protected CardService cardService;
    @Autowired
    protected ScoreboardService scoreboardService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected RoundService roundService;

    // CRUD
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(int id) {
        return gameRepository.findById(id);
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public Round findRoundByGameAndNumber(int roundNumber, int gameId) {
        return gameRepository.findRoundByGameAndNumber(roundNumber, gameId);
    }

    // Funciones
    @Transactional
    public Game createGame(){
        Game game = new Game();
        game.setNumberOfPlayers(1);
        game.setNumberOfRounds(2);
        game.setCreator(userService.findCurrentUser().getUsername());
        gameRepository.save(game);
        return game;
    }
}
