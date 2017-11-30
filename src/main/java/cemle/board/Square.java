package cemle.board;

public class Square {

    private int row;
    private int col;
    private int index;

    public static int count = 0;

    // For Kryo
    Square() {
        count++;
    }

    Square(int index) {
        count++;
        this.index = index;
        this.row = index / 8;
        this.col = index % 8;
    }

    Square(int row, int col) {
        count++;
        this.row = row;
        this.col = col;
        this.index = row * 8 + col;
    }

    public boolean isOnBoard() {
        return !(row < 0 || row > 7 || col < 0 || col > 7);
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

    public Square getOffsetSquare(int rowOffset, int colOffset) {
        return Squares.get(row + rowOffset, col + colOffset);
    }

    public boolean isOffsetOnBoard(int rowOffset, int colOffset) {
        int r = row + rowOffset;
        int c = col + colOffset;
        return r >= 0 && r <= 7 && c >= 0 && c <= 7;
    }

}
