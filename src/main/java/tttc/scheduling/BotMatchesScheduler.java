package tttc.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tttc.model.Player;
import tttc.repository.PlayerRepository;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class BotMatchesScheduler {
    private final Random rand = new Random();
    private final PlayerRepository playerRepository;


    @Scheduled(fixedRate = 5000)
    public void scheduleMatches() {
        if (playerRepository.count() < 2) {
            log.info("Not enough players to schedule a match.");
            return;
        }
        Player player1 = playerRepository.findRandomBotPlayer();
        Player player2 = playerRepository.findRandomBotPlayer();
        while (player1.getId() == player2.getId()) {
            player2 = playerRepository.findRandomBotPlayer();
        }

        if (rand.nextBoolean()) {
            player1.setGames(player1.getGames() + 1);
            player1.setWins(player1.getWins() + 1);
            player2.setGames(player2.getGames() + 1);
            player2.setLoses(player2.getLoses() + 1);
        } else if (rand.nextBoolean()) {
            player2.setGames(player2.getGames() + 1);
            player2.setWins(player2.getWins() + 1);
            player1.setGames(player1.getGames() + 1);
            player1.setLoses(player1.getLoses() + 1);
        } else {
            player1.setGames(player1.getGames() + 1);
            player1.setTies(player1.getTies() + 1);
            player2.setGames(player2.getGames() + 1);
            player2.setTies(player2.getTies() + 1);
        }
        playerRepository.saveAll(List.of(player1, player2));
        log.info("Scheduled match between {} and {}", player1.getName(), player2.getName());
    }
}
