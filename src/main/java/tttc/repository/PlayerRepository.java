package tttc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tttc.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query(value = "SELECT * FROM player WHERE is_bot = true ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Player findRandomBotPlayer();
}
