package tttc.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tttc.model.Player;
import tttc.resource.LeaderBoardPlayerResource;

@Component
public class PlayerToPlayerResourceConverter implements Converter<Player, LeaderBoardPlayerResource> {

    @Override
    public LeaderBoardPlayerResource convert(Player source) {
        if (source == null) {
            return null;
        }
        LeaderBoardPlayerResource resource = new LeaderBoardPlayerResource();
        resource.setName(source.getName());
        resource.setGames(String.valueOf(source.getGames()));
        resource.setWins(String.valueOf(source.getWins()));
        resource.setTies(String.valueOf(source.getTies()));
        resource.setLoses(String.valueOf(source.getLoses()));
        resource.setRank(String.valueOf(source.getRank()));
        return resource;
    }
}
