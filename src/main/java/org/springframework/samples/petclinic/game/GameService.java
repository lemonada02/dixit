package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.scoreboard.Scoreboard;
import org.springframework.samples.petclinic.scoreboard.ScoreboardService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // FUNCTIONS
    @Transactional
    public void playerCreateGame(String username, Game game) {
        game.setNumberOfPlayers(1);
        gameRepository.save(game);

        User user = userService.findUser(username);
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.setGame(game);
        scoreboard.setUser(user);
        scoreboard.setScore(0);
        scoreboard.setOrder(1);
        scoreboardService.save(scoreboard);
    }

    @Transactional
    public void playerJoinGame(String username, Game game) {
        if (game.getNumberOfPlayers() < 4) {
            game.setNumberOfPlayers(game.getNumberOfPlayers() + 1);
            game.setNumberOfRounds((game.getNumberOfPlayers() + 1) * 2);
            gameRepository.save(game);

            User user = userService.findUser(username);
            Scoreboard scoreboard = new Scoreboard();

            scoreboard.setGame(game);
            scoreboard.setUser(user);
            scoreboard.setScore(0);
            scoreboard.setOrder(game.getNumberOfPlayers());
            scoreboardService.save(scoreboard);
        }
    }

    @Transactional
    public void playerLeaveGame(String username, Game game) {
        game.setNumberOfPlayers(game.getNumberOfPlayers() - 1);
        game.setNumberOfRounds((game.getNumberOfPlayers() - 1) * 2);
        gameRepository.save(game);

        Scoreboard scoreboard = scoreboardService.findByGameIdAndUsername(game.getId(), username);
        scoreboardService.deleteById(scoreboard.getId());
    }

    @Transactional
    public void initGame(Integer gameId) {
        if (cardService.findAllCards().isEmpty()) {
            cardService.createAllCards();
        }
        List<Card> cards = cardService.findAllCards();
        Game game = findById(gameId);
        game.setCards(cards);
        game.setRounds(new ArrayList<Round>());
        gameRepository.save(game);
    }

    @Transactional
    public void initialDeal(Game game, User user) {
        List<Card> cards = game.getCards();
        Collections.shuffle(cards);
        user.setMazo(cards.subList(0, 6));
        userService.saveUser(user);
        cards.removeAll(user.getMazo());
        game.setCards(cards);
    }

    @Transactional
    public void playCard(Card carta, User user, Game game) {
        int roundNumber = (game.getRounds().size());
        Round round = new Round();
        if (findRoundByGameAndNumber(game.getId(), roundNumber) != null) {
            round = findRoundByGameAndNumber(game.getId(), roundNumber);
        } else{
            round.setRoundNumber(roundNumber);
        }

        List<Card> selected = round.getSelectedCards().isEmpty() ? new ArrayList<>() : round.getSelectedCards();
        selected.add(carta);
        user.setCartaSeleccionada(carta);
        round.setSelectedCards(selected);
        roundService.save(round);
        userService.saveUser(user);

        List<Round> rounds = game.getRounds();
        if (!rounds.contains(round)) {
            rounds.add(round);
            game.setRounds(rounds);
        } else {
            rounds.set(rounds.indexOf(round), round);
            game.setRounds(rounds);
        }
        gameRepository.save(game);
    }

    @Transactional
    public void sendTheme(String theme, Game game) {
        int roundNumber = (game.getRounds().size());
        Round round = new Round();
        if (findRoundByGameAndNumber(game.getId(), roundNumber) != null) {
            round = findRoundByGameAndNumber(game.getId(), roundNumber);
        }

        round.setRoundNumber(roundNumber);
        round.setTheme(theme);
        roundService.save(round);

        List<Round> rounds = game.getRounds();
        if (!rounds.contains(round)) {
            rounds.add(round);
            game.setRounds(rounds);
        } else {
            rounds.set(rounds.indexOf(round), round);
            game.setRounds(rounds);
        }
        gameRepository.save(game);
    }

    @Transactional
    public void chooseNarrator(User user, Game game) {
        int roundNumber = (game.getRounds().size());
        int players = game.getNumberOfPlayers();
        Scoreboard scoreboard = scoreboardService.findByGameIdAndUsername(game.getId(), user.getUsername());
        int order = scoreboard.getOrder();

        if (players == 3) {
            if (order == roundNumber || order == roundNumber + 3) {
                user.setIsNarrator(true);
                userService.saveUser(user);
            } else {
                user.setIsNarrator(false);
                userService.saveUser(user);
            }
        } else {
            if (order == roundNumber || order == roundNumber + 4) {
                user.setIsNarrator(true);
                userService.saveUser(user);
            } else {
                user.setIsNarrator(false);
                userService.saveUser(user);
            }
        }
    }

    @Transactional
    public void chooseCard(Card carta, User user, Game game) {
        user.setCartaElegida(carta);
        userService.saveUser(user);

        Round round = findRoundByGameAndNumber(game.getId(), game.getRounds().size());
        Map<Card, Integer> votes = round.getVotes();
        if (votes.containsKey(carta)) {
            votes.put(carta, votes.get(carta) + 1);
        } else {
            votes.put(carta, 1);
        }

        round.setVotes(votes);
        roundService.save(round);
    }

    @Transactional
    public void asignScore(User user, Game game){
        Card carta= user.getCartaElegida();
        Scoreboard scoreboard = scoreboardService.findByGameIdAndUsername(game.getId(), user.getUsername());
        Round round = findRoundByGameAndNumber(game.getId(), game.getRounds().size());
        Card cartaCorrecta= round.getCorrectCard();

        if(cartaCorrecta == carta){
            scoreboard.setScore(scoreboard.getScore() + 3);
            scoreboardService.save(scoreboard);
        } 

        Map<Card, Integer> votes = round.getVotes();
        if(votes.containsKey(carta)){
            scoreboard.setScore(scoreboard.getScore() + votes.get(carta));
        } 

        if (!votes.containsKey(cartaCorrecta) && !user.getIsNarrator()){
            scoreboard.setScore(scoreboard.getScore() + 2);
        }

        scoreboardService.save(scoreboard);
    }
}
