package org.springframework.samples.petclinic.scoreboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/scoreboards")
@SecurityRequirement(name = "bearerAuth")
public class ScoreboardController {

    private ScoreboardService scoreboardService;

    @Autowired
    public ScoreboardController(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }
}
