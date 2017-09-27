package moves;

import board.Board;

public class SpecialMove implements Move {

    private int[][] moves;

    public SpecialMove(int[][] moves) {
        this.moves = moves;
    }

    public int[][] getMoves() {
        return moves;
    }

    @Override
    public void move(Board board) {
        board.specialMove(this.moves);
    }

    @Override
    public MoveType getType() {
        return (moves.length == 4) ? MoveType.CASTLING : MoveType.NORMAL;
    }
}
