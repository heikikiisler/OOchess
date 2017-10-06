package moves;

import board.Board;
import board.Square;

public class PawnDoubleMove implements Move {

    private Square startSquare;
    private Square endSquare;

    public PawnDoubleMove(Square startSquare, Square endSquare) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
    }

    @Override
    public void move(Board board) {
        if (endSquare.getRow() == 7 || endSquare.getRow() == 0) {
            System.out.println("PawnDoubleMove.move");
        }
        board.pawnDoubleMove(startSquare, endSquare);
    }
}
