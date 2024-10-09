package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.exceptions.TooManyPlayersException;
import org.springframework.samples.petclinic.scoreboard.Scoreboard;
import org.springframework.samples.petclinic.scoreboard.ScoreboardService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/games")
@SecurityRequirement(name = "bearerAuth")
public class GameController {

    private final String GAME_LISTING = "games/joinGameListing";
    private final String GAME_DETAILS = "games/gameDetails";
    private final String FINISH_GAME = "games/finishGame";
    private final String WON_GAME = "games/wonGame";


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

    //por revisar
    @Transactional
    @GetMapping("/new")
    public ModelAndView newGame() {
        ModelAndView res = new ModelAndView(GAME_DETAILS);
        Game game = new Game();
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.setUser(userService.findCurrentUser());
        scoreboard.setOrder(1);
        scoreboard.setScore(0);
        scoreboard.setGame(game);
        scoreboardService.save(scoreboard);
        game.setNumberOfPlayers(1);
        game.setScoreboards(List.of(scoreboard));
        gameService.save(game);
        return new ModelAndView("redirect:/api/v1/games/gameListing");
    }
}