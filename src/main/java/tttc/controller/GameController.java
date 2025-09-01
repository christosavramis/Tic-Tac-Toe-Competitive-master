package tttc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tttc.model.Player;
import tttc.resource.APIWrapperResponse;
import tttc.resource.CreateOrJoinGameResource;
import tttc.service.GameService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    // ---------------- GET PLAYER INFO ----------------
    @PostMapping("/start")
    public Map<String, Object> startGame() {
        gameService.initializePlayers();
        Player p1 = gameService.getPlayer1();
        Player p2 = gameService.getPlayer2();

        return Map.of("player1Name", p1.getName(), "player1Rank", p1.getRank(), "player2Name", p2.getName(), "player2Rank", p2.getRank());
    }

    // ---------------- SET STARTER AND START GAME ----------------
    @PostMapping("/starter")
    public Map<String, String> setStarter(@RequestBody Map<String, String> payload) {
        String starterKey = payload.get("starter"); // "player1" or "player2"

        gameService.startGame(starterKey);

        return Map.of("message", "Starter set successfully", "starter", starterKey);
    }


    @PostMapping("game/create")
    public ResponseEntity<?> createGame(@RequestBody Map<String, String> payload) {
        String playerName = payload.get("playerName");
        if (playerName == null || playerName.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing playerName"));
        }

        // For simplicity, we just return a success message
        return ResponseEntity.ok(Map.of("message", "Game created successfully", "playerName", playerName));
    }

//  expecting to be called with something like domain.com/api/v1/game/testgameid/join
// why i return ResponseEntity<?> well i haven't created the proper resource class yet :)
    @PostMapping("/game/{gameId}/join")
    public ResponseEntity<?> joinGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(APIWrapperResponse.of(gameService.joinGame(gameId)));
    }

    @GetMapping("/game")
    public ResponseEntity<?> getGameState() {
        return ResponseEntity.ok(APIWrapperResponse.of(null));
    }
}
