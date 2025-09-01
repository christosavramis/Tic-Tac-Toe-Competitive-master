package tttc.model;

public class Board {

    private char[] board;

    public Board() {
        board = new char[9];
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
    }

    public char[] getBoard() {
        return board;
    }

    public boolean setMark(int position, char mark) {
        if (position < 0 || position >= 9 || board[position] != ' ') return false;
        board[position] = mark;
        return true;
    }

    public boolean isFull() {
        for (char cell : board) {
            if (cell == ' ') return false;
        }
        return true;
    }

    public void resetBoard() {
        for (int i = 0; i < 9; i++) board[i] = ' ';
    }
}
