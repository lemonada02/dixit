package org.springframework.samples.petclinic.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cards")
public class CardController {
    
    private final String CARD_LISTING= "cards/listing";

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Transactional
    @GetMapping("/listing")
    public ModelAndView listCards() {
        ModelAndView mav = new ModelAndView(CARD_LISTING);
        cardService.createAllCards();
        mav.addObject("cards", cardService.findAllCards());
        return mav;
    }
}
