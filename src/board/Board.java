package board;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Square> squares = new ArrayList<>(64);

    private boolean whiteKingCastling = true;
    private boolean whiteQueenCastling = true;
    private boolean blackKingCastling = true;
    private boolean blackQueenCastling = true;


    public Board() {
        makeSquares();
    }

    private void makeSquares() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares.add(new Square(i, j));
            }
        }
    }

    public Square getSquare(int row, int col) {
        return squares.get(8 * row + col);
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares.get(i + j * 8).printPiece();
            }
            System.out.println();
        }
    }
}
