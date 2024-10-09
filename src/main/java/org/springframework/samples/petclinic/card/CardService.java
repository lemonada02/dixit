package org.springframework.samples.petclinic.card;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    
    protected CardRepository cardRepository;

    @Autowired
    CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> findAllCards() {
        return cardRepository.findAll();
    }

    public Card findCardById(int cardId) {
        return cardRepository.findById(cardId);
    }

    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    public void createAllCards(){

        List<String> nombres = Arrays.asList(
            "https://imgur.com/V2bDYNN.png",
            "https://imgur.com/FmyBknh.png",
            "https://imgur.com/ikR9zsr.png",
            "https://imgur.com/JU5HpmQ.png",
            "https://imgur.com/PN1f1tJ.png",
            "https://imgur.com/EPklRdO.png",
            "https://imgur.com/TtgTxGJ.png",
            "https://imgur.com/c1f42n4.png",
            "https://imgur.com/Q1FmETb.png",
            "https://imgur.com/0mavrtt.png"
        );

        for (int i = 0; i < nombres.size(); i++) {
            Card carta = new Card();
            carta.setDesign(nombres.get(i));
            saveCard(carta);
        }


    }
}
