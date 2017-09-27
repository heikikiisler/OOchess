package moves;

import board.Square;

public class SetMove {

    private Square square;
    private char piece;

    public SetMove(Square square, char piece) {
        this.square = square;
        this.piece = piece;
    }

    public Square getSquare() {
        return square;
    }

    public char getPiece() {
        return piece;
    }
}
