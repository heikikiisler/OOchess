package board;

import side.Side;

// Main place for ideas and comments
// TODO: 19.09.2017 3 move draw rule (move history?)
// TODO: 19.09.2017 Evaluation class
// TODO: 19.09.2017 Game / Running class
// TODO: 19.09.2017 LATER: Engine input compatible with international standards
public class Board {

    private char[] pieces;

    private char[] whitePieces = new char[]{'P', 'R', 'N', 'B', 'Q', 'K'};
    private char[] blackPieces = new char[]{'p', 'r', 'n', 'b', 'q', 'k'};

    private boolean whiteKingSideCastling = true;
    private boolean whiteQueenSideCastling = true;
    private boolean blackKingSideCastling = true;
    private boolean blackQueenSideCastling = true;

    private int totalPlies = 0;
    private int currentInactivePlies = 0;

    private int enPassantRow;
    private int enPassantCol;

    private boolean whiteInCheck = false;
    private boolean blackInCheck = false;

    private boolean whiteInCheckMate = false;
    private boolean blackInCheckMate = false;

    private Side sideToMove;
    private Side result;


    public Board() {
        setUpBoard();
    }

    public void move(int start_row, int start_col, int end_row, int end_col) {
        char piece = getPiece(start_row, start_col);
        totalPlies++;
        currentInactivePlies++;
        enPassantRow = 0;
        enPassantCol = 0;
        // Reset 50 move rule counter
        if (Character.toLowerCase(piece) == 'p' || getPiece(end_row, end_col) != '.') {
            currentInactivePlies = 0;
            System.out.println("inactive reset");
        }
        // Set en passant coordinates
        if (Character.toLowerCase(piece) == 'p' && (Math.abs(start_row - end_row) == 2)) {
            enPassantRow = Math.abs((start_row + end_row) / 2);
            enPassantCol = start_col;
            System.out.println(String.format("en passant: %d, %d", enPassantRow, enPassantCol));
        }
        // Castling rights
        if (piece == 'K') {
            whiteQueenSideCastling = false;
            whiteKingSideCastling = false;
        } else if (piece == 'k') {
            blackQueenSideCastling = false;
            blackKingSideCastling = false;
        } else if (start_row == 0 && start_col == 0) {
            whiteQueenSideCastling = false;
        } else if (start_row == 0 && start_col == 7) {
            whiteKingSideCastling = false;
        } else if (start_row == 7 && start_col == 0) {
            blackQueenSideCastling = false;
        } else if (start_row == 7 && start_col == 7) {
            blackKingSideCastling = false;
        }
        // Finally move piece
        setPiece(piece, end_row, end_col);
        setPiece('.', start_row, start_col);
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

    public Side getPieceSide(char piece) {
        if (piece == '.') {
            return Side.NEUTRAL;
        } else {
            for (char c: whitePieces) {
                if (piece == c) {
                    return Side.WHITE;
                }
            }
        }
        return Side.BLACK;
    }

    private void setUpBoard() {
        sideToMove = Side.WHITE;
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

}
