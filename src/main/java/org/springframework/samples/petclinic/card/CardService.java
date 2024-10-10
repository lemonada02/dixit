package org.springframework.samples.petclinic.card;

import java.util.List;
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
        if(!cardRepository.findAll().isEmpty()){
            return cardRepository.findAll();
        } else {
            createAllCards();
            return cardRepository.findAll();
        }
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
            "https://imgur.com/0mavrtt.png",
            "https://imgur.com/1gwvy9a.png",
            "https://imgur.com/CBsVKl9.png",
            "https://imgur.com/DZ4OhZO.png",
            "https://imgur.com/tpkzqqK.png",
            "https://imgur.com/thkGJuL.png",
            "https://imgur.com/24YkltD.png",
            "https://imgur.com/8xiocDf.png",
            "https://imgur.com/5IBKqVe.png",
            "https://imgur.com/T2lsph4.png",
            "https://imgur.com/0jA0lbM.png",
            "https://imgur.com/d9hCUIA.png",
            "https://imgur.com/yx3F4xV.png",
            "https://imgur.com/oivNzbP.png",
            "https://imgur.com/UUgHRox.png",
            "https://imgur.com/0ZQIk1B.png",
            "https://imgur.com/kOWYwUP.png",
            "https://imgur.com/ioFtIyz.png",
            "https://imgur.com/6t655zG.png",
            "https://imgur.com/4B8aEZ3.png",
            "https://imgur.com/IPDrukK.png",
            "https://imgur.com/YlOGtRy.png",
            "https://imgur.com/61vU3is.png",
            "https://imgur.com/fBRglZu.png",
            "https://imgur.com/PtcdJ2Q.png",
            "https://imgur.com/YVmaDaC.png",
            "https://imgur.com/3QdYWhU.png"
        );

        for (int i = 0; i < nombres.size(); i++) {
            Card carta = new Card();
            carta.setDesign(nombres.get(i));
            saveCard(carta);
        }
    }
}
