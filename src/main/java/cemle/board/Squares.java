package cemle.board;

public class Squares {

    private static final Square[] squares = getSquares();

    public static Square get(int index) {
        return squares[index];
    }

    public static Square get(int row, int col) {
        return get(row * 8 + col);
    }

    public static Square[] getAll() {
        return squares;
    }

    private static Square[] getSquares() {
        Square[] squares = new Square[64];
        for (int i = 0; i < 64; i++) {
            squares[i] = new Square(i);
        }
        return squares;
    }

}
