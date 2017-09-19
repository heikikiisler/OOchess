package piece;

import side.Side;

public class Knight extends Piece {

    public Knight(Side side, int row, int col) {
        super(side, row, col);
    }

    @Override
    char getLetter() {
        return 'k';
    }
}
