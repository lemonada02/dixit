package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/cards")
@SecurityRequirement(name = "bearerAuth")
public class CardController {
    
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Transactional
    @GetMapping("/listing")
    public List<Card> showCardListing() {
        if (cardService.findAllCards().isEmpty()) {
            cardService.createAllCards();
        }
        return cardService.findAllCards();
    }
}
