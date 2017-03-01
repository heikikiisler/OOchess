package board;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<List<Square>> squares = new ArrayList<>(8);

    private boolean whiteKingCastling = true;
    private boolean whiteQueenCastling = true;
    private boolean blackKingCastling = true;
    private boolean blackQueenCastling = true;

    // TODO: 01/03/2017 3 move draw rule
    private int currentDrawMoves = 0;

    private Square enPassantSquare;

    private boolean whiteInCheck = false;
    private boolean blackInCheck = false;


    private boolean whiteInCheckMate = false;
    private boolean blackInCheckMate = false;



    public Board() {
        makeSquares();
    }

    private void makeSquares() {
        for (int i = 0; i < 8; i++) {
            squares.set(i, new ArrayList<Square>(8));
        }
    }

    public void printBoard() {
        for (List<Square> row : squares) {
            for (Square square : row) {
                square.printPiece();
            }
            System.out.println();
        }
    }

    private void makeStartPosition() {

    }
}
