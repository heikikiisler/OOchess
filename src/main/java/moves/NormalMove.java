package moves;

import board.Board;

public class NormalMove implements Move {

    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;

    public NormalMove(int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    @Override
    public void move(Board board) {
        board.move(this);
    }

}
