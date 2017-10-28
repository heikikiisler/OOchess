package moves;

import board.Board;
import board.Square;
import board.Squares;

public class NormalMove implements Move {

    private Square startSquare;
    private Square endSquare;

    public NormalMove(Square startSquare, Square endSquare) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
    }

    public static NormalMove get(int startRow, int startCol, int endRow, int endCol) {
        return new NormalMove(Squares.get(startRow, startCol), Squares.get(endRow, endCol));
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
