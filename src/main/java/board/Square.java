package board;

public class Square {

    private int row;
    private int col;
    private int index;

    public Square(int index) {
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        this.index = row * 8 + col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getIndex() {
        return index;
    }

}
