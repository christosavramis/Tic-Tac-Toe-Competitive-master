package tttc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PostPersist;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tttc.exception.GenericGameException;

import java.util.stream.Stream;

@Entity
@Getter
@Setter
public class Game extends AbstractEntity {
    private String playerX;
    private String playerO;
    private String currentPlayer;
    private GAME_STATE gameState = GAME_STATE.INITIAL;
    private String persistentBoard = "........."; // Serialized board state e.g "O.X.....X"
    private String timelineBoard = ".........."; // the timeline of all board states
    private transient String[][] board = new String[][]{{".", ".", "."}, {".", ".", "."}, {".", ".", "."}}; // 3x3 board representation

    private String serializeBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    @PostPersist
    public void syncBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = String.valueOf(persistentBoard.charAt(i * 3 + j));
            }
        }
    }

    public void joinPlayer(String playerName) {
        if (playerX != null && playerX.equals(playerName) || playerO != null && playerO.equals(playerName)) {
            // Player is reconnecting
            return;
        }
        if (playerX == null) {
            playerX = playerName;
            currentPlayer = playerX;
        } else if (playerO == null) {
            playerO = playerName;
        } else {
            throw new GenericGameException("Game is full");
        }
        if (playerX != null && playerO != null) {
            this.gameState = GAME_STATE.IN_PROGRESS;
        }
        setVersion(getVersion() + 1);
    }

    public void placeMark(int row, int col, String player) {
        if (gameState != GAME_STATE.IN_PROGRESS) {
            throw new GenericGameException("Game is not in progress");
        }
        if (!player.equals(currentPlayer)) {
            throw new GenericGameException("It's not your turn");
        }
        String mark = player.equals(playerX) ? CELL_TYPE.X.getValue() : CELL_TYPE.O.getValue();
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            throw new GenericGameException("Invalid board position %d %d".formatted(row, col));
        }
        if (Stream.of(board).flatMap(Stream::of).noneMatch(cell -> cell.equals(CELL_TYPE.EMPTY.getValue()))) {
            throw new GenericGameException("The board is full");
        }
        if (!board[row][col].equals(CELL_TYPE.EMPTY.getValue())) {
            throw new GenericGameException("Cell is already occupied");
        }
        board[row][col] = mark;
        this.gameState = calculateGameState();
        switch (this.gameState) {
            case X_WON, O_WON, TIE -> this.currentPlayer = null;
            case IN_PROGRESS -> this.currentPlayer = this.currentPlayer.equals(playerX) ? playerO : playerX;
            default -> {}
        }
        this.persistentBoard = serializeBoard();
        this.timelineBoard += persistentBoard;
        setVersion(getVersion() + 1);
    }

    private GAME_STATE calculateGameState() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals(CELL_TYPE.EMPTY.getValue())) {
                return GAME_STATE.parseWin(board[i][0]);
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals(CELL_TYPE.EMPTY.getValue())) {
                return GAME_STATE.parseWin(board[0][i]);
            }
        }
        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals(CELL_TYPE.EMPTY.getValue())) {
            return GAME_STATE.parseWin(board[0][0]);
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals(CELL_TYPE.EMPTY.getValue())) {
            return GAME_STATE.parseWin(board[0][2]);
        }
        // Check for tie or in-progress
        boolean isBoardFull = Stream.of(board).flatMap(Stream::of).noneMatch(cell -> cell.equals(CELL_TYPE.EMPTY.getValue()));
        return isBoardFull ? GAME_STATE.TIE : GAME_STATE.IN_PROGRESS;
    }

    @RequiredArgsConstructor
    @Getter
    enum CELL_TYPE {
        X("X"), O("O"), EMPTY(".");
        private final String value;
    }

    @RequiredArgsConstructor
    @Getter
    enum GAME_STATE {
        INITIAL("INITIAL"), IN_PROGRESS("IN_PROGRESS"), X_WON("X_WON"), O_WON("O_WON"), TIE("TIE");
        private final String value;

        public static GAME_STATE parseWin(String winningMark) {
            return winningMark.equals(CELL_TYPE.X.getValue()) ? X_WON : O_WON;
        }
    }
}
