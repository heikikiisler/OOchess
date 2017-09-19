package board;

import piece.*;
import side.Side;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Piece> pieces;

    private boolean whiteKingCastling = true;
    private boolean whiteQueenCastling = true;
    private boolean blackKingCastling = true;
    private boolean blackQueenCastling = true;

    private int currentPlies = 0;
    private int currentInactivePlies = 0;

    private int[] enPassantSquare;

    private boolean whiteInCheck = false;
    private boolean blackInCheck = false;

    private boolean whiteInCheckMate = false;
    private boolean blackInCheckMate = false;

    private Side sideToMove = Side.WHITE;
    private Side winner = Side.NEUTRAL;


    public Board() {
        setUpBoard();
    }

    private void setUpBoard() {
        pieces = new ArrayList<>(64);
        for (int i = 0; i < 64; i++) {
            pieces.add(new Empty(Side.NEUTRAL, i / 8, i % 8));
        }
        for (int i = 0; i < 8; i++) {
            setPiece(new Pawn(Side.WHITE, 1, i), 1, i);
            setPiece(new Pawn(Side.BLACK, 6, i), 6, i);
        }
        setPiece(new Rook(Side.WHITE, 0, 0), 0, 0);
        setPiece(new Rook(Side.WHITE, 0, 7), 0, 7);
        setPiece(new Knight(Side.WHITE, 0, 6), 0, 6);
        setPiece(new Knight(Side.WHITE, 0, 1), 0, 1);
        setPiece(new Bishop(Side.WHITE, 0, 2), 0, 2);
        setPiece(new Bishop(Side.WHITE, 0, 5), 0, 5);
        setPiece(new Queen(Side.WHITE, 0, 3), 0, 3);
        setPiece(new King(Side.WHITE, 0, 4), 0, 4);

        setPiece(new Rook(Side.BLACK, 7, 0), 7, 0);
        setPiece(new Rook(Side.BLACK, 7, 7), 7, 7);
        setPiece(new Knight(Side.BLACK, 7, 6), 7, 6);
        setPiece(new Knight(Side.BLACK, 7, 1), 7, 1);
        setPiece(new Bishop(Side.BLACK, 7, 2), 7, 2);
        setPiece(new Bishop(Side.BLACK, 7, 5), 7, 5);
        setPiece(new Queen(Side.BLACK, 7, 3), 7, 3);
        setPiece(new King(Side.BLACK, 7, 4), 7, 4);
    }

    public void printBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 7; j >= 0; j--) {
                getPiece(i, j).printLetter();
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private Piece getPiece(int row, int col) {
        return pieces.get(row * 8 + col);
    }

    private void setPiece(Piece piece, int row, int col) {
        pieces.set(row * 8 + col, piece);
    }

}
