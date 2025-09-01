package tttc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameViewController {
    @GetMapping
    public String game() {
        return "/index.html";
    }

    @GetMapping("/leaderboard")
    public String scoreboard() {
        return "/leaderboard.html";
    }

    @GetMapping("/game")
    public String gameView() {
        return "/game.html";
    }
}
