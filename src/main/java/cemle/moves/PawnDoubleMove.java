package cemle.moves;

import cemle.board.Board;
import cemle.board.Square;

public class PawnDoubleMove implements Move {

    private Square startSquare;
    private Square endSquare;

    public PawnDoubleMove(Square startSquare, Square endSquare) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
    }

    @Override
    public void move(Board board) {
        board.pawnDoubleMove(startSquare, endSquare);
    }
}
