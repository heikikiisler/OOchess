package piece;

import side.Side;

public abstract class Piece {

    private int row;
    private int col;
    Side side;

    public Piece(Side side, int row, int col) {
        this.side = side;
        this.row = row;
        this.col = col;
    }

    abstract char getLetter();

    public void printLetter() {
        char letter = getLetter();
        if (side.equals(Side.WHITE)) {
            System.out.print(Character.toUpperCase(letter));
        } else {
            System.out.print(letter);
        }
    }
}
