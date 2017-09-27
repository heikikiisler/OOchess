package moves;

import board.Board;
import board.Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;


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

    public void getAvailableMoves(int row, int col) {
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

    public void getAllAvailableMoves() {
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

    public void getKnightMoves(int row, int col) {
        int[][][] vectors = {{{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}}};
        getVectorMoves(row, col, vectors);
    }

    public void getKingMoves(int row, int col) {
        int[][][] vectors = {{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}};
        getVectorMoves(row, col, vectors);
    }

    public void getRookMoves(int row, int col) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}}};
        getVectorMoves(row, col, vectors);
    }

    public void getBishopMoves(int row, int col) {
        int[][][] vectors =
            {{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        getVectorMoves(row, col, vectors);
    }

    public void getQueenMoves(int row, int col) {
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

    public void getPawnMoves(int row, int col) {
        int[][][] takingVectors = {{{1, 1}, {1, -1}}};
        if (board.getPiece(row + side, col) == '.') {
            addNormalMove(row, col, row + side, col);
            if (board.getPiece(row + side * 2, col) == '.' && (side == 1 && row == 1 || side == -1 && row == 6)) {
                addNormalMove(row, col, row + side * 2, col);
            }
        }
        getVectorMoves(row, col, takingVectors);
    }

    public void getPromotionMoves() {
        // TODO: 27.09.2017 Clean up code
        for (int col = 0; col < 8; col++) {
            if (side == 1) {
                if (board.getPiece(6, col) == 'P' && board.getPiece(7, col) == '.') {
                    for (char option : board.getWhiteQueeningPieces()) {
                        specialMoves.add(
                                new SpecialMove(new int[][]{{'.', 6, col}, {option, 7, col}})
                        );
                    }
                }
            } else {
                if (board.getPiece(1, col) == 'p' && board.getPiece(0, col) == '.') {
                    for (char option : board.getBlackQueeningPieces()) {
                        specialMoves.add(
                                new SpecialMove(new int[][]{{'.', 1, col}, {option, 0, col}})
                        );
                    }
                }
            }
        }
    }

    public void getCastlingMoves() {
        // TODO: 27.09.2017 Clean up code
        if (side == 1) {
            if (
                    board.getWhiteQueenSideCastling() &&
                    board.getPiece(0, 1) == '.' &&
                    board.getPiece(0, 2) == '.' &&
                    board.getPiece(0, 3) == '.'
                ) {
                specialMoves.add(
                        new SpecialMove(new int[][]{{'.', 0, 0}, {'K', 0, 2}, {'R', 0, 3}, {'.', 0, 4}})
                );
            }
            if (
                    board.getWhiteKingSideCastling() &&
                    board.getPiece(0, 5) == '.' &&
                    board.getPiece(0, 6) == '.'
                ) {
                specialMoves.add(
                        new SpecialMove(new int[][]{{'.', 0, 4}, {'K', 0, 6}, {'R', 0, 5}, {'.', 0, 7}})
                );
            }
        } else {
            if (
                    board.getBlackQueenSideCastling() &&
                    board.getPiece(7, 1) == '.' &&
                    board.getPiece(7, 2) == '.' &&
                    board.getPiece(7, 3) == '.'
                ) {
                specialMoves.add(
                        new SpecialMove(new int[][]{{'.', 7, 0}, {'k', 7, 2}, {'r', 7, 3}, {'.', 7, 4}})
                );
            }
            if (
                    board.getBlackKingSideCastling() &&
                    board.getPiece(7, 5) == '.' &&
                    board.getPiece(7, 6) == '.'
                ) {
                specialMoves.add(
                        new SpecialMove(new int[][]{{'.', 7, 4}, {'k', 7, 6}, {'r', 7, 5}, {'.', 7, 7}})
                );
            }
        }
    }

    public void getEnPassantMoves() {
        // TODO: 27.09.2017 Clean up code
        if (board.getEnPassantRow() != 0) {
            int r = board.getEnPassantRow();
            int c = board.getEnPassantCol();
            if (side == 1) {
                if (isOnBoard(r - 1, c - 1) && board.getPiece(r - 1, c - 1) == 'P') {
                    specialMoves.add(
                            new SpecialMove(new int[][]{{'P', r, c}, {'.', r - 1, c - 1}, {'.', r - 1, c}})
                    );
                }
                if (isOnBoard(r - 1, c + 1) && board.getPiece(r - 1, c + 1) == 'P') {
                    specialMoves.add(
                            new SpecialMove(new int[][]{{'P', r, c}, {'.', r - 1, c + 1}, {'.', r - 1, c}})
                    );
                }
            } else {
                if (isOnBoard(r + 1, c - 1) && board.getPiece(r + 1, c - 1) == 'p') {
                    specialMoves.add(
                            new SpecialMove(new int[][]{{'p', r, c}, {'.', r + 1, c - 1}, {'.', r + 1, c}})
                    );
                }
                if (isOnBoard(r - 1, c + 1) && board.getPiece(r + 1, c + 1) == 'p') {
                    specialMoves.add(
                            new SpecialMove(new int[][]{{'p', r, c}, {'.', r + 1, c + 1}, {'.', r + 1, c}})
                    );
                }
            }
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
