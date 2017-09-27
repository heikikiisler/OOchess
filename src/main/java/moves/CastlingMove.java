package moves;

import board.Board;

public class CastlingMove implements Move {

    private boolean kingSide;

    public CastlingMove(boolean kingSide) {
        this.kingSide = kingSide;
    }

    @Override
    public void move(Board board) {
        board.castlingMove(kingSide);
    }

}
