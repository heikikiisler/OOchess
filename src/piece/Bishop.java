package piece;

import side.Side;

public class Bishop extends Piece {

    public Bishop(Side side, int row, int col) {
        super(side, row, col);
    }

    @Override
    char getLetter() {
        return 'b';
    }
}
