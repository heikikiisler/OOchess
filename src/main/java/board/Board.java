package board;

import com.esotericsoftware.kryo.Kryo;
import moves.Move;
import moves.NormalMove;

import java.util.ArrayList;


public class Board {

//    private static final Kryo KRYO = new Kryo();
    private static final char[] WHITE_PIECES = new char[]{'P', 'R', 'N', 'B', 'Q', 'K'};
    private static final char[] BLACK_PIECES = new char[]{'p', 'r', 'n', 'b', 'q', 'k'};
    private static final char[] WHITE_PROMOTION_PIECES = new char[]{'R', 'N', 'B', 'Q'};
    private static final char[] BLACK_PROMOTION_PIECES = new char[]{'r', 'n', 'b', 'q'};
    private char[] pieces;
    private boolean whiteKingSideCastling = true;
    private boolean whiteQueenSideCastling = true;
    private boolean blackKingSideCastling = true;
    private boolean blackQueenSideCastling = true;

    private int sideToMove;

    private int totalPlies;
    private int currentInactivePlies;

    private Square enPassantSquare;

    private ArrayList<Square> disallowedCheckSquares;

    public Board() {
        setUpBoard();
    }

    private Board(boolean setUp) {

    }

    public static int getPieceSide(char piece) {
        if (piece == '.') {
            return 0;
        } else {
            for (char c : WHITE_PIECES) {
                if (piece == c) {
                    return 1;
                }
            }
        }
        return -1;
    }

    public static boolean pieceIsSide(char piece, int side) {
        return getPieceSide(piece) == side;
    }

    public static char[] getWhitePromotionPieces() {
        return WHITE_PROMOTION_PIECES;
    }

    public static char[] getBlackPromotionPieces() {
        return BLACK_PROMOTION_PIECES;
    }

    private void setUpBoard() {
        disallowedCheckSquares = new ArrayList<>();
        sideToMove = 1;
        totalPlies = 0;
        currentInactivePlies = 0;
        pieces = new char[64];
        for (int i = 0; i < 64; i++) {
            pieces[i] = '.';
        }
        for (int i = 0; i < 8; i++) {
            setPiece('P', 1, i);
            setPiece('p', 6, i);
        }
        setPiece('R', 0, 0);
        setPiece('R', 0, 7);
        setPiece('N', 0, 6);
        setPiece('N', 0, 1);
        setPiece('B', 0, 2);
        setPiece('B', 0, 5);
        setPiece('Q', 0, 3);
        setPiece('K', 0, 4);

        setPiece('r', 7, 0);
        setPiece('r', 7, 7);
        setPiece('n', 7, 6);
        setPiece('n', 7, 1);
        setPiece('b', 7, 2);
        setPiece('b', 7, 5);
        setPiece('q', 7, 3);
        setPiece('k', 7, 4);
    }

    public void move(NormalMove move) {
        char piece = getPiece(move.getStartSquare());
        if (getPiece(move.getEndSquare()) == '.') {
            currentInactivePlies++;
        } else {
            currentInactivePlies = 0;
        }
        if (getPiece(move.getStartSquare()) == '.') {
            printBoard();
            System.out.println("Start square '.'");
        }
        if (getPiece(move.getEndSquare()) == 'K' || getPiece(move.getEndSquare()) == 'k') {
            printBoard();
            System.out.println("Taking king");
        }
        // Castling rights
        if (piece == 'K') {
            whiteQueenSideCastling = false;
            whiteKingSideCastling = false;
        } else if (piece == 'k') {
            blackQueenSideCastling = false;
            blackKingSideCastling = false;
        } else if (move.getStartSquare() == Squares.get(0, 0)) {
            whiteQueenSideCastling = false;
        } else if (move.getStartSquare() == Squares.get(0, 7)) {
            whiteKingSideCastling = false;
        } else if (move.getStartSquare() == Squares.get(7, 0)) {
            blackQueenSideCastling = false;
        } else if (move.getStartSquare() == Squares.get(7, 7)) {
            blackKingSideCastling = false;
        }
        // Finally move piece
        setPiece(piece, move.getEndSquare());
        setPiece('.', move.getStartSquare());
        refreshAlways();
    }

    public void move(Move move) {
        move.move(this);
    }

    public void castlingMove(boolean kingSide) {
        refreshAlways();
        currentInactivePlies++;
        if (sideToMove == 1) {
            if (kingSide) {
                setPiece('.', Squares.get(0, 4));
                setPiece('R', Squares.get(0, 5));
                setPiece('K', Squares.get(0, 6));
                setPiece('.', Squares.get(0, 7));
                disallowedCheckSquares.add(Squares.get(0, 4));
                disallowedCheckSquares.add(Squares.get(0, 5));
            } else {
                setPiece('.', Squares.get(0, 0));
                setPiece('K', Squares.get(0, 2));
                setPiece('R', Squares.get(0, 3));
                setPiece('.', Squares.get(0, 4));
                disallowedCheckSquares.add(Squares.get(0, 1));
                disallowedCheckSquares.add(Squares.get(0, 3));
                disallowedCheckSquares.add(Squares.get(0, 4));
            }
            whiteKingSideCastling = false;
            whiteQueenSideCastling = false;
        } else {
            if (kingSide) {
                setPiece('.', Squares.get(7, 4));
                setPiece('r', Squares.get(7, 5));
                setPiece('k', Squares.get(7, 6));
                setPiece('.', Squares.get(7, 7));
                disallowedCheckSquares.add(Squares.get(7, 4));
                disallowedCheckSquares.add(Squares.get(7, 5));

            } else {
                setPiece('.', Squares.get(7, 0));
                setPiece('k', Squares.get(7, 2));
                setPiece('r', Squares.get(7, 3));
                setPiece('.', Squares.get(7, 4));
                disallowedCheckSquares.add(Squares.get(7, 1));
                disallowedCheckSquares.add(Squares.get(7, 3));
                disallowedCheckSquares.add(Squares.get(7, 4));
            }
            blackKingSideCastling = false;
            blackQueenSideCastling = false;
        }
    }

    public void promotionMove(Square startSquare, Square endSquare, char piece) {
        setPiece(piece, endSquare);
        setPiece('.', startSquare);
        enPassantSquare = null;
        currentInactivePlies = 0;
        refreshAlways();
    }

    public void enPassantMove(Square startSquare) {
        System.out.println(String.format("Piece: %c, start square: %d, %d", getPiece(startSquare), startSquare.getRow(), startSquare.getCol()));
        setPiece(getPiece(startSquare), enPassantSquare);
        setPiece('.', startSquare);
        setPiece('.', enPassantSquare.getOffsetSquare(-sideToMove, 0));
        refreshAlways();
        currentInactivePlies = 0;
    }

    public void pawnDoubleMove(Square startSquare, Square endSquare) {
        move(new NormalMove(startSquare, endSquare));
        currentInactivePlies = 0;
        enPassantSquare = startSquare.getOffsetSquare(-sideToMove, 0);
    }

    private void refreshAlways() {
        totalPlies++;
        disallowedCheckSquares.clear();
        disallowedCheckSquares.add(getKingPosition(sideToMove));
        sideToMove = -sideToMove;
        enPassantSquare = null;
    }

    public void printBoard() {
        System.out.println();
        System.out.println(String.format("Move #%d; Ply #%d", (totalPlies + 1) / 2, totalPlies));
        System.out.println();
        for (int i = 7; i >= 0; i--) {
            System.out.print(i + "   ");
            for (int j = 0; j < 8; j++) {
                System.out.print(getPiece(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("    0 1 2 3 4 5 6 7");
        System.out.println();
    }

    public char getPiece(int row, int col) {
        return pieces[row * 8 + col];
    }

    public char getPiece(Square square) {
        return pieces[square.getIndex()];
    }

    private void setPiece(char piece, int row, int col) {
        pieces[row * 8 + col] = piece;
    }

    private void setPiece(char piece, Square square) {
        pieces[square.getIndex()] = piece;
    }

    public int getPieceSide(Square square) {
        return getPieceSide(getPiece(square));
    }

    public ArrayList<Square> getDisallowedCheckSquares() {
        return disallowedCheckSquares;
    }

    public Square getKingPosition(int side) {
        char king = (side == 1) ? 'K' : 'k';
        for (int i = 0; i < 64; i++) {
            if (pieces[i] == king) {
                return Squares.get(i);
            }
        }
        printBoard();
        throw new IllegalStateException(String.format("King not found, side: %s", side));
    }

    public boolean isSquareEmpty(Square square) {
        return getPiece(square) == '.';
    }

    public Square getEnPassantSquare() {
        return enPassantSquare;
    }

    public char[] getPieces() {
        return pieces;
    }

    public int getSideToMove() {
        return sideToMove;
    }

    public Board getCopy() {
//        return KRYO.copy(this);
        Board copy = new Board(false);
        copy.pieces = new char[64];
        System.arraycopy(pieces, 0, copy.pieces, 0, 64);
        copy.whiteKingSideCastling = whiteKingSideCastling;
        copy.whiteQueenSideCastling = whiteQueenSideCastling;
        copy.blackKingSideCastling = blackKingSideCastling;
        copy.blackQueenSideCastling = blackQueenSideCastling;

        copy.sideToMove = sideToMove;

        copy.totalPlies = totalPlies;
        copy.currentInactivePlies = currentInactivePlies;

        copy.enPassantSquare = enPassantSquare;

        copy.disallowedCheckSquares = disallowedCheckSquares;
        return copy;
    }

    public boolean isCastlingAllowed(boolean kingSide) {
        if (sideToMove == 1) {
            if (kingSide) {
                return whiteKingSideCastling;
            } else {
                return whiteQueenSideCastling;
            }
        } else {
            if (kingSide) {
                return blackKingSideCastling;
            } else {
                return blackQueenSideCastling;
            }
        }
    }
}
