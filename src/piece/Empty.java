package piece;

import side.Side;

public class Empty extends Piece {

    public Empty(Side side, int row, int col) {
        super(side, row, col);
    }

    @Override
    char getLetter() {
        return '.';
    }
}
