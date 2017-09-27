package moves;

import board.Board;
import board.Square;

public class EnPassantMove implements Move {

    private Square startSquare;

    public EnPassantMove(Square startSquare) {
        this.startSquare = startSquare;
    }

    @Override
    public void move(Board board) {
        board.enPassantMove(startSquare);
    }
}
