package moves;

import board.Square;

public class AttackMove extends NormalMove {

    private char endPiece;

    public AttackMove(Square startSquare, Square endSquare, char endPiece) {
        super(startSquare, endSquare);
        this.endPiece = endPiece;
    }

    public char getEndPiece() {
        return endPiece;
    }

}
