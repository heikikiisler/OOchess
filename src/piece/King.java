package piece;

import side.Side;

public class King extends Piece {

    public King(Side side, int row, int col) {
        super(side, row, col);
    }

    @Override
    char getLetter() {
        return 'k';
    }
}
