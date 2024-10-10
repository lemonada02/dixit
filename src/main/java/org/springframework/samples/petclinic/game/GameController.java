package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.scoreboard.Scoreboard;
import org.springframework.samples.petclinic.scoreboard.ScoreboardService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/games")
@SecurityRequirement(name = "bearerAuth")
public class GameController {

    private GameService gameService;
    private ScoreboardService scoreboardService;
    private UserService userService;
    private CardService cardService;

    @Autowired
    public GameController(GameService gameService, ScoreboardService scoreboardService, UserService userService, CardService cardService) {
        this.gameService = gameService;
        this.scoreboardService = scoreboardService;
        this.userService = userService;
        this.cardService = cardService;
    }

    //funciona
    @Transactional
    @GetMapping("/gameListing")
    public List<Game> showGameListing() {
        return gameService.findAll();
    }

    @Transactional
    @GetMapping("/new")
    public Game newGame() {
        return gameService.createGame();   
    }
}