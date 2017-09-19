package piece;

import side.Side;

public class Queen extends Piece {

    public Queen(Side side, int row, int col) {
        super(side, row, col);
    }

    @Override
    char getLetter() {
        return 'q';
    }
}
