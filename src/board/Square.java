package board;

import piece.Piece;

public class Square {

    private int row;
    private int col;
    private Piece piece;


    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getValue() {
        return piece.getValue();
    }

    public void printPiece() {
        if (piece != null) {
            System.out.printf(piece.getPieceLetter());
        }
        System.out.printf(".");
    }
}
