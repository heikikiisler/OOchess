package moves;

public class AttackMove extends NormalMove {

    private char endPiece;

    public AttackMove(int startRow, int startCol, int endRow, int endCol, char endPiece) {
        super(startRow, startCol, endRow, endCol);
        this.endPiece = endPiece;
    }

    public char getEndPiece() {
        return endPiece;
    }
}
