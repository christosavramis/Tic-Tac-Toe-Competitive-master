package tttc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tttc.converters.PlayerToPlayerResourceConverter;
import tttc.exception.GenericGameException;
import tttc.model.Board;
import tttc.model.Game;
import tttc.model.Player;
import tttc.repository.GameRepository;
import tttc.repository.PlayerRepository;
import tttc.session.SessionPlayer;

@Service
@RequiredArgsConstructor
public class GameService {
    private final SessionPlayer sessionPlayer;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository; // <-- inject repository
    private final PlayerToPlayerResourceConverter playerToPlayerResourceConverter;

    private Player player1;
    private Player player2;
    private Player starter; // who goes first
    private Board board;

    public void initializePlayers() {
        player1 = new Player("Swag", "High Schooler");
        player2 = new Player("AI", "Easy");
        starter = null; // no starter chosen yet
        board = null;   // board not started yet
    }

    public void startGame(String starterKey) {
        if (player1 == null || player2 == null) {
            throw new IllegalStateException("Players not initialized");
        }

        board = new Board();
        board.resetBoard();

        starter = "player1".equals(starterKey) ? player1 : player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getStarter() {
        return starter;
    }

    public Board getBoard() {
        return board;
    }

    public Object createGame() {
        Player sessionPlayerObject = sessionPlayer.getObject();
        Game game = new Game();
        game.joinPlayer(sessionPlayerObject.getName());
        Game savedGame = gameRepository.save(game);
        sessionPlayerObject.setCurrentGame(savedGame);
        return savedGame;
    }

    public Object joinGame(Long gameId) {
        Player sessionPlayerObject = sessionPlayer.getObject();
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GenericGameException("Game not found"));
        game.joinPlayer(sessionPlayerObject.getName());
        Game savedGame = gameRepository.save(game);
        sessionPlayerObject.setCurrentGame(savedGame);
        notifyPlayers(savedGame);
        return savedGame;
    }

    public Object placeMark(int row, int col) {
        Player sessionPlayerObject = sessionPlayer.getObject();
        Game game = sessionPlayerObject.getCurrentGame();
        if (game == null) {
            throw new GenericGameException("Player is not in a game");
        }
        game.placeMark(row, col, sessionPlayerObject.getName());

        Game savedGame = gameRepository.save(game);
        sessionPlayerObject.setCurrentGame(savedGame);
        notifyPlayers(savedGame);
        return savedGame;
    }

    private void notifyPlayers(Game game) {
        System.out.println(game);
    }
}
