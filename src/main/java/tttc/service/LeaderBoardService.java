package tttc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tttc.converters.PlayerToPlayerResourceConverter;
import tttc.exception.GenericGameException;
import tttc.model.Player;
import tttc.repository.PlayerRepository;
import tttc.resource.LeaderBoardPlayerResource;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaderBoardService {
    private final PlayerRepository playerRepository;
    private final PlayerToPlayerResourceConverter playerToPlayerResourceConverter;

    public List<LeaderBoardPlayerResource> getAllProfiles(Integer limit) {
        return playerRepository.findAll()
                .stream()
                .sorted((p1, p2) -> Integer.compare(p2.getElo(), p1.getElo())) // Sort by wins descending
                .limit(limit)
                .map(playerToPlayerResourceConverter::convert)
                .toList();
    }

    public LeaderBoardPlayerResource getProfileById(long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isEmpty()) {
            throw new GenericGameException("Player not found");
        }
        return playerToPlayerResourceConverter.convert(optionalPlayer.get());
    }

}
