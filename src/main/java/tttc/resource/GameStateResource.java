package tttc.resource;

import lombok.Data;

@Data
public class GameStateResource {
    private boolean isGameEnded;
    private String gameResult;
    private String currentPlayer;
}
