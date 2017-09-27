package moves;

import board.Square;

public class DefendMove {

    private Square startSquare;
    private Square endSquare;
    private char endPiece;

    public DefendMove(Square startSquare, Square endSquare, char endPiece) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.endPiece = endPiece;
    }

    public Square getStartSquare() {
        return startSquare;
    }

    public Square getEndSquare() {
        return endSquare;
    }

    public char getEndPiece() {
        return endPiece;
    }
}
