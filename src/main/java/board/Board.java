package board;

import moves.NormalMove;

// Main place for ideas and comments
// TODO: 19.09.2017 3 move draw rule (move history?)
// TODO: 24.09.2017 Universal move structure (probably excluding special moves) 
// TODO: 23.09.2017 LATER: Save PGN for every game
// TODO: 19.09.2017 LATER: Engine input compatible with international standards
public class Board {

    private char[] pieces;

    private static final char[] WHITE_PIECES = new char[]{'P', 'R', 'N', 'B', 'Q', 'K'};
    private static final char[] BLACK_PIECES = new char[]{'p', 'r', 'n', 'b', 'q', 'k'};

    private static final char[] WHITE_QUEENING_PIECES = new char[]{'R', 'N', 'B', 'Q'};
    private static final char[] BLACK_QUEENING_PIECES = new char[]{'r', 'n', 'b', 'q'};

    private boolean whiteKingSideCastling = true;
    private boolean whiteQueenSideCastling = true;
    private boolean blackKingSideCastling = true;
    private boolean blackQueenSideCastling = true;

    private int totalPlies = 0;
    private int currentInactivePlies = 0;

    private int enPassantRow;
    private int enPassantCol;

    public Board() {
        setUpBoard();
    }

    public void move(NormalMove move) {
        char piece = getPiece(move.getStartRow(), move.getStartCol());
        refreshAlways();
        // Reset 50 move rule counter
        if (Character.toLowerCase(piece) == 'p' || getPiece(move.getEndRow(), move.getEndCol()) != '.') {
            resetInactivePlies();
        }
        // Set en passant coordinates
        if (Character.toLowerCase(piece) == 'p' && (Math.abs(move.getStartRow() - move.getEndRow()) == 2)) {
            enPassantRow = Math.abs((move.getStartRow() + move.getEndRow()) / 2);
            enPassantCol = move.getStartCol();
        }
        // Castling rights
        if (piece == 'K') {
            whiteQueenSideCastling = false;
            whiteKingSideCastling = false;
        } else if (piece == 'k') {
            blackQueenSideCastling = false;
            blackKingSideCastling = false;
        } else if (move.getStartRow() == 0 && move.getStartCol() == 0) {
            whiteQueenSideCastling = false;
        } else if (move.getStartRow() == 0 && move.getStartCol() == 7) {
            whiteKingSideCastling = false;
        } else if (move.getStartRow() == 7 && move.getStartCol() == 0) {
            blackQueenSideCastling = false;
        } else if (move.getStartRow() == 7 && move.getStartCol() == 7) {
            blackKingSideCastling = false;
        }
        // Finally move piece
        setPiece(piece, move.getEndRow(), move.getEndCol());
        setPiece('.', move.getStartRow(), move.getStartCol());
    }

    public void specialMove(int[][] coordinateValues) {
        refreshAlways();
        if (coordinateValues.length < 4) {
            resetInactivePlies();
        } else {
            if (coordinateValues[1][0] == 'K') {
                whiteQueenSideCastling = false;
                whiteKingSideCastling = false;
            } else {
                blackQueenSideCastling = false;
                blackKingSideCastling = false;
            }
        }
        for (int[] coo: coordinateValues) {
            setPiece((char)(coo[0]), coo[1], coo[2]);
        }
    }

    private void resetInactivePlies() {
        currentInactivePlies = 0;
    }


    private void refreshAlways() {
        totalPlies++;
        currentInactivePlies++;
        enPassantRow = 0;
        enPassantCol = 0;
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

    private void setPiece(char piece, int row, int col) {
        pieces[row * 8 + col] = piece;
    }

    public static int getPieceSide(char piece) {
        if (piece == '.') {
            return 0;
        } else {
            for (char c: WHITE_PIECES) {
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

    private void setUpBoard() {
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

    public int getEnPassantRow() {
        return enPassantRow;
    }

    public int getEnPassantCol() {
        return enPassantCol;
    }

    public boolean getWhiteKingSideCastling() {
        return whiteKingSideCastling && getPiece(0, 6) == '.' && getPiece(0, 5) == '.';
    }

    public boolean getWhiteQueenSideCastling() {
        return whiteQueenSideCastling && getPiece(0, 1) == '.' && getPiece(0, 2) == '.' && getPiece(0, 3) == '.';
    }

    public boolean getBlackKingSideCastling() {
        return blackKingSideCastling;
    }

    public boolean getBlackQueenSideCastling() {
        return blackQueenSideCastling;
    }

    public char[] getWhiteQueeningPieces() {
        return WHITE_QUEENING_PIECES;
    }

    public char[] getBlackQueeningPieces() {
        return BLACK_QUEENING_PIECES;
    }

    public char[] getPieces() {
        return pieces;
    }
}
