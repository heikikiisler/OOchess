package moves;

import board.Board;
import board.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;


public class Moves {

    public Board board;
    private int side;
    private ArrayList<Move> normalMoves;
    private ArrayList<Move> attackMoves;
    private ArrayList<DefendMove> defendMoves;
    private ArrayList<Move> specialMoves;
    private Set<Square> attackedSquares;

    public Moves(Board board) {
        this.board = board;
        this.side = this.board.getSideToMove();
        this.normalMoves = new ArrayList<>();
        this.attackMoves = new ArrayList<>();
        this.defendMoves = new ArrayList<>();
        this.specialMoves = new ArrayList<>();
        this.attackedSquares = Collections.emptySet();
        getAllAvailableMoves();
    }

    private void getAvailableMoves(int row, int col) {
        char piece = board.getPiece(row, col);
        switch (piece) {
            case 'P':
                getPawnMoves(row, col);
            case 'N':
                getKnightMoves(row, col);
            case 'B':
                getBishopMoves(row, col);
            case 'R':
                getRookMoves(row, col);
            case 'Q':
                getQueenMoves(row, col);
            case 'K':
                getKingMoves(row, col);
            case 'p':
                getPawnMoves(row, col);
            case 'n':
                getKnightMoves(row, col);
            case 'b':
                getBishopMoves(row, col);
            case 'r':
                getRookMoves(row, col);
            case 'q':
                getQueenMoves(row, col);
            case 'k':
                getKingMoves(row, col);
            default: throw new IllegalArgumentException();
        }
    }

    private void getAllAvailableMoves() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                char piece = board.getPiece(r, c);
                if (Board.pieceIsSide(piece, side)) {
                    getAvailableMoves(r, c);
                }
            }
        }
        getEnPassantMoves();
        getCastlingMoves();
        getPromotionMoves();
    }

    private boolean isOnBoard(int row, int col) {
        return !(row < 0 || row > 7 || col < 0 || col > 7);
    }

    private void getKnightMoves(int row, int col) {
        int[][][] vectors = {{{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}}};
        getVectorMoves(row, col, vectors);
    }

    private void getKingMoves(int row, int col) {
        int[][][] vectors = {{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}};
        getVectorMoves(row, col, vectors);
    }

    private void getRookMoves(int row, int col) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}}};
        getVectorMoves(row, col, vectors);
    }

    private void getBishopMoves(int row, int col) {
        int[][][] vectors =
            {{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        getVectorMoves(row, col, vectors);
    }

    private void getQueenMoves(int row, int col) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}},
            {{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        getVectorMoves(row, col, vectors);
    }

    private void getVectorMoves(int row, int col, int[][][] vectors) {
        for (int[][] subVectors: vectors) {
            for (int[] vector: subVectors) {
                int r = vector[0] + row;
                int c = vector[1] + col;
                if (isOnBoard(r, c)) {
                    char targetPiece = board.getPiece(r, c);
                    if (targetPiece == '.') {
                        addNormalMove(row, col, r, c);
                    } else if (Board.getPieceSide(targetPiece) != side) {
                        addAttackMove(row, col, r, c, targetPiece);
                        break;
                    } else {
                        addDefendMove(row, col, r, c, targetPiece);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    // TODO: 27.09.2017 Capturing moves
    private void getPawnMoves(int row, int col) {
        if (side == 1 && row == 6) {
            // TODO: 27.09.2017 Add white promotion moves
        } else if (side == -1 && row == 1) {
            // TODO: 27.09.2017 Add black promotion moves
        // Forward moves
        } else if (board.getPiece(row + side, col) == '.') {
            addNormalMove(row, col, row + side, col);
            if (board.getPiece(row + side * 2, col) == '.' && (side == 1 && row == 1 || side == -1 && row == 6)) {
                addNormalMove(row, col, row + side * 2, col);
            }
        }

    }

    private void getPromotionMoves() {
        // TODO: 27.09.2017 Clean up code
//        int row = (side == 1) ? 6 : 1;
//        int pawn = (side == 1) ? 'P' : 'p';
//        char[] promotionPieces = (side == 1) ? Board.getWhitePromotionPieces() : Board.getBlackPromotionPieces();
//        for (int col = 0; col < 8; col++) {
//                if (board.getPiece(row, col) == pawn && board.getPiece(row + side, col) == '.') {
//                    for (char option : board.getWhiteQueeningPieces()) {
//                        specialMoves.add(new PromotionMove(new Square(row + side, col)));
//                    }
//        }
    }

    private void getCastlingMoves() {
        if (side == 1) {
            getCastlingMove(false, new Square(0, 1), new Square(0, 2), new Square(0, 3));
            getCastlingMove(true, new Square(0, 5), new Square(0, 6));
        } else {
            getCastlingMove(false, new Square(7, 1), new Square(7, 2), new Square(7, 3));
            getCastlingMove(true, new Square(7, 5), new Square(7, 6));
        }
    }

    private void getCastlingMove(boolean kingSide, Square... empties) {
        Predicate<Square> emptyCheck = square -> board.getPiece(square) == '.';
        if (board.isCastlingAllowed(kingSide) && Arrays.stream(empties).allMatch(emptyCheck)) {
            specialMoves.add(new CastlingMove(kingSide));
        }
    }

    private void getEnPassantMoves() {
        // TODO: 27.09.2017 Clean up code
        if (board.getEnPassantRow() != 0) {
            int r = board.getEnPassantRow();
            int c = board.getEnPassantCol();
            getEnPassantMove(new Square(r - side, c - 1));
            getEnPassantMove(new Square(r - side, c + 1));
        }
    }

    private void getEnPassantMove(Square startSquare) {
        char piece = (side == 1) ? 'P' : 'p';
        if (startSquare.isOnBoard() && board.getPiece(startSquare) == piece) {
            specialMoves.add(new EnPassantMove(startSquare));
        }
    }

    private void addNormalMove(int startRow, int startCol, int endRow, int endCol) {
        normalMoves.add(new NormalMove(startRow, startCol, endRow, endCol));
        attackedSquares.add(new Square(endRow, endCol));
    }

    private void addAttackMove(int startRow, int startCol, int endRow, int endCol, char targetPiece) {
        attackMoves.add(new AttackMove(startRow, startCol, endRow, endCol, targetPiece));
        attackedSquares.add(new Square(endRow, endCol));
    }

    private void addDefendMove(int startRow, int startCol, int endRow, int endCol, char targetPiece) {
        defendMoves.add(new DefendMove(startRow, startCol, endRow, endCol, targetPiece));
    }

    public ArrayList<Move> getNormalMoves() {
        return normalMoves;
    }

    public ArrayList<Move> getAttackMoves() {
        return attackMoves;
    }

    public ArrayList<DefendMove> getDefendMoves() {
        return defendMoves;
    }

    public ArrayList<Move> getSpecialMoves() {
        return specialMoves;
    }

    public Set<Square> getAttackedSquares() {
        return attackedSquares;
    }

    public ArrayList<Move> getAllPossibleMoves() {
        ArrayList<Move> allMoves = new ArrayList<>();
        allMoves.addAll(normalMoves);
        allMoves.addAll(attackMoves);
        allMoves.addAll(specialMoves);
        return allMoves;
    }
}
