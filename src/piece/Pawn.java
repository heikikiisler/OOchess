package piece;

import side.Side;

public class Pawn extends Piece {

    public Pawn(Side side, int row, int col) {
        super(side, row, col);
    }

    @Override
    char getLetter() {
        return 'p';
    }

}
