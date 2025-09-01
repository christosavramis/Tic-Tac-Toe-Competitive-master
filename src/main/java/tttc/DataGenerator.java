package tttc;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tttc.model.Player;
import tttc.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator implements CommandLineRunner {
    private final PlayerRepository playerRepository;
    private final Faker faker = new Faker();

    public DataGenerator(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) {
        if(!playerRepository.findAll().isEmpty()){
            return;
        }
        List<Player> players = new ArrayList<>();
        for(int i = 1; i <= 30; i++) {
            Player player = new Player();
            player.setName(faker.name().username());
            players.add(player);
            int randomGames = (int) (Math.random() * 50);
            int wins = (int) (Math.random() * randomGames * 0.7); // wins should be less than games
            int ties = (int) (Math.random() * (randomGames - wins) * 0.5); // ties should be less than remaining games
            int loses = randomGames - wins - ties; // loses is the rest
            player.setGames(randomGames);
            player.setWins(wins);
            player.setTies(ties);
            player.setLoses(loses);
            player.setElo(1000 + (wins * 10) - (loses * 5) + (ties * 2));
            player.setBot(true);
        }
        playerRepository.saveAll(players);
        System.out.println("created " + playerRepository.count() + " players");
    }
}
