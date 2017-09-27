package moves;

import board.Board;
import board.Square;

public class NormalMove implements Move {

    private Square startSquare;
    private Square endSquare;

    public NormalMove(Square startSquare, Square endSquare) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
    }

    public Square getStartSquare() {
        return startSquare;
    }

    public Square getEndSquare() {
        return endSquare;
    }

    @Override
    public void move(Board board) {
        board.move(this);
    }

}
