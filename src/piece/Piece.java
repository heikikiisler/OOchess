package piece;

public abstract class Piece {

    private int row;
    private int col;

    public Piece(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String getLetter() {
        return ".";
    }
}
