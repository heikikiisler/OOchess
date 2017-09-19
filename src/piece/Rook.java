package piece;

import side.Side;

public class Rook extends Piece {

    public Rook(Side side, int row, int col) {
        super(side, row, col);
    }

    @Override
    char getLetter() {
        return 'r';
    }
}
