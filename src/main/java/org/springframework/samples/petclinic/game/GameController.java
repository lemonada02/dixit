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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
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

    @Transactional
    @GetMapping("/new")
    public ModelAndView newGame(ModelMap model, Principal principal) throws TooManyPlayersException {
        Game game = new Game();
        cardService.createAllCards();
        game.setCards(cardService.findAllCards());
        game.setNumberOfPlayers(1);
        model.addAttribute(game);
        gameService.save(game);

        gameService.playerCreateGame(principal.getName(), game);
        gameService.save(game);
        
        gameService.playerJoinGame(principal.getName(), game);
        gameService.initGame(game.getId());
        return new ModelAndView("redirect:/games/"+game.getId()+"/view");
    }

    @Transactional
    @GetMapping("/gameListing")
    public ModelAndView showGameListing(ModelMap model) {
        List<Game> games = gameService.findAll();
        ModelAndView res = new ModelAndView(GAME_LISTING);
        res.addObject("games", games);
        return res;
    }

    @Transactional
    @GetMapping("/{gameId}/view")
    public ModelAndView showDetails(ModelMap model, @PathVariable("gameId") int gameId) {

        Game game = gameService.findById(gameId);
        List<Scoreboard> scoreboards = scoreboardService.findByGameId(gameId);

        ModelAndView res = new ModelAndView(GAME_DETAILS);
        res.addObject("game", game);
        res.addObject("scoreboards", scoreboards);
        return res;
    }

    @Transactional
    @GetMapping("/{gameId}/join/{username}")
    public ModelAndView joinGame(@PathVariable("gameId") int gameId, @PathVariable("username") String username) {
        Game game = gameService.findById(gameId);
        try{
            gameService.playerJoinGame(username, game);
        } catch (TooManyPlayersException e) {
            return new ModelAndView("redirect:/games");
        }

        return new ModelAndView("redirect:/games/"+gameId+"/view");
    }
}
