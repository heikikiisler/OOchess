package moves;

public class DefendMove {

    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;
    private char endPiece;

    public DefendMove(int startRow, int startCol, int endRow, int endCol, char endPiece) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.endPiece = endPiece;
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

    public char getEndPiece() {
        return endPiece;
    }
}
