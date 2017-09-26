package moves;

import board.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

// TODO: 19.09.2017 Filter moves that leave king in check
// TODO: 20.09.2017 piece attacking and defending functions
// TODO: 20.09.2017 Add move start coordinates
// Example move list
// int[][][] {
//      {{start_row, start_col}},
//      {{end_row, end_col}, {end_row, end_col}, ...},
//      {{att_row, att_col, att_piece}, {att_row, att_col, att_piece}, ...},
//      {{def_row, def_col, def_piece}, {def_row, def_col, def_piece}, ...}
// }
public class Moves {

    public Board board;
    private ArrayList<Move> normalMoves;
    private ArrayList<Move> attackMoves;
    private ArrayList<DefendMove> defendMoves;
    private ArrayList<Move> specialMoves;
    private Set<int[]> attackedSquares;

    public Moves(Board board) {
        this.board = board;
        this.normalMoves = new ArrayList<>();
        this.attackMoves = new ArrayList<>();
        this.defendMoves = new ArrayList<>();
        this.specialMoves = new ArrayList<>();
        this.attackedSquares = Collections.emptySet();
    }

    public void getAvailableMoves(int row, int col) {
        char piece = board.getPiece(row, col);
        switch (piece) {
            case 'P':
                getPawnMoves(1, row, col);
            case 'N':
                getKnightMoves(1, row, col);
            case 'B':
                getBishopMoves(1, row, col);
            case 'R':
                getRookMoves(1, row, col);
            case 'Q':
                getQueenMoves(1, row, col);
            case 'K':
                getKingMoves(1, row, col);
            case 'p':
                getPawnMoves(-1, row, col);
            case 'n':
                getKnightMoves(-1, row, col);
            case 'b':
                getBishopMoves(-1, row, col);
            case 'r':
                getRookMoves(-1, row, col);
            case 'q':
                getQueenMoves(-1, row, col);
            case 'k':
                getKingMoves(-1, row, col);
            default: throw new IllegalArgumentException();
        }
    }

    public void getAllAvailableMoves(int side) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                char piece = board.getPiece(r, c);
                if (Board.pieceIsSide(piece, side)) {
                    getAvailableMoves(r, c);
                }
            }
        }
        getEnPassantMoves(side);
        getCastlingMoves(side);
        getPromotionMoves(side);
    }

    private boolean isOnBoard(int row, int col) {
        return !(row < 0 || row > 7 || col < 0 || col > 7);
    }

    public void getKnightMoves(int side, int row, int col) {
        int[][][] vectors = {{{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}}};
        getVectorMoves(side, row, col, vectors);
    }

    public void getKingMoves(int side, int row, int col) {
        int[][][] vectors = {{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}}};
        getVectorMoves(side, row, col, vectors);
    }

    public void getRookMoves(int side, int row, int col) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}}};
        getVectorMoves(side, row, col, vectors);
    }

    public void getBishopMoves(int side, int row, int col) {
        int[][][] vectors =
            {{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        getVectorMoves(side, row, col, vectors);
    }

    public void getQueenMoves(int side, int row, int col) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}},
            {{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        getVectorMoves(side, row, col, vectors);
    }

    private void getVectorMoves(int side, int row, int col, int[][][] vectors) {
        for (int[][] subVectors: vectors) {
            for (int[] vector: subVectors) {
                int r = vector[0] + row;
                int c = vector[1] + col;
                if (isOnBoard(r, c)) {
                    char targetPiece = board.getPiece(r, c);
                    if (targetPiece == '.') {
                        normalMoves.add(new NormalMove(row, col, r, c));
                    } else if (Board.getPieceSide(targetPiece) != side) {
                        attackMoves.add(new AttackMove(row, col, r, c, targetPiece));
                        break;
                    } else {
                        defendMoves.add(new DefendMove(row, col, r, c, targetPiece));
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    public void getPawnMoves(int side, int row, int col) {
        int[][][] takingVectors = {{{1, 1}, {1, -1}}};
        if (board.getPiece(row + side, col) == '.') {
            normalMoves.add(new NormalMove(row, col, row + side, col));
            if (board.getPiece(row + side * 2, col) == '.' && (side == 1 && row == 1 || side == -1 && row == 6)) {
                normalMoves.add(new NormalMove(row, col, row + side * 2, col));
            }
        }
        getVectorMoves(side, row, col, takingVectors);
    }

    public void getPromotionMoves(int side) {
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

    public void getCastlingMoves(int side) {
        if (side == 1) {
            if (
                    board.getWhiteQueenSideCastling() &&
                    board.getPiece(0, 1) == '.' &&
                    board.getPiece(0, 2) == '.' &&
                    board.getPiece(0, 3) == '.'
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
                ) {
                specialMoves.add(
                        new SpecialMove(new int[][]{{'.', 0, 0}, {'K', 0, 2}, {'R', 0, 3}, {'.', 0, 4}})
                );
            }
            if (
                    board.getWhiteKingSideCastling() &&
                    board.getPiece(0, 5) == '.' &&
                    board.getPiece(0, 6) == '.'
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
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
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
                ) {
                specialMoves.add(
                        new SpecialMove(new int[][]{{'.', 7, 0}, {'k', 7, 2}, {'r', 7, 3}, {'.', 7, 4}})
                );
            }
            if (
                    board.getBlackKingSideCastling() &&
                    board.getPiece(7, 5) == '.' &&
                    board.getPiece(7, 6) == '.'
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
                ) {
                specialMoves.add(
                        new SpecialMove(new int[][]{{'.', 7, 4}, {'k', 7, 6}, {'r', 7, 5}, {'.', 7, 7}})
                );
            }
        }
    }

    public void getEnPassantMoves(int side) {
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

    public Set<int[]> getAttackedSquares() {
        return attackedSquares;
    }
}
