package moves;

import board.Board;
import side.Side;

import java.util.ArrayList;

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

    private Board board;

    public Moves(Board board) {
        this.board = board;
    }

    public ArrayList<int[]> getAvailableMoves(int row, int col) {
        char piece = board.getPiece(row, col);
        switch (piece) {
            case 'P':
                return getPawnMoves(Side.WHITE, row, col);
            case 'N':
                return getKnightMoves(Side.WHITE, row, col);
            case 'B':
                return getBishopMoves(Side.WHITE, row, col);
            case 'R':
                return getRookMoves(Side.WHITE, row, col);
            case 'Q':
                return getQueenMoves(Side.WHITE, row, col);
            case 'K':
                return getKingMoves(Side.WHITE, row, col);
            case 'p':
                return getPawnMoves(Side.BLACK, row, col);
            case 'n':
                return getKnightMoves(Side.BLACK, row, col);
            case 'b':
                return getBishopMoves(Side.BLACK, row, col);
            case 'r':
                return getRookMoves(Side.BLACK, row, col);
            case 'q':
                return getQueenMoves(Side.BLACK, row, col);
            case 'k':
                return getKingMoves(Side.WHITE, row, col);
            default: throw new IllegalArgumentException();
        }
    }

    public ArrayList<ArrayList<int[]>> getAllAvailableMoves(Side side) {
        ArrayList<ArrayList<int[]>> moves = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                char piece = board.getPiece(r, c);
                if (board.pieceIsSide(piece, side)) {
                    moves.add(getAvailableMoves(r, c));
                }
            }
        }
        return moves;
    }

    private boolean isOnBoard(int row, int col) {
        return !(row < 0 || row > 7 || col < 0 || col > 7);
    }

    public boolean isAvailable(Side side, int row, int col) {
        return isOnBoard(row, col) && side != board.getPieceSide(board.getPiece(row, col));
    }

    public ArrayList<int[]> getKnightMoves(Side side, int row, int col) {
        int[][] vectors = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        return getSingleVectorMoves(side, row, col, vectors);
    }

    public ArrayList<int[]> getKingMoves(Side side, int row, int col) {
        int[][] vectors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        return getSingleVectorMoves(side, row, col, vectors);
    }

    private ArrayList<int[]> getSingleVectorMoves(Side side, int row, int col, int[][] vectors) {
        ArrayList<int[]> moves = new ArrayList<>();
        for (int[] vector: vectors) {
            int r = vector[0] + row;
            int c = vector[1] + col;
            if (isAvailable(side, r, c)) {
                moves.add(new int[]{r, c});
            }
        }
        return moves;
    }

    public ArrayList<int[]> getRookMoves(Side side, int row, int col) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}}};
        return findVectorMoves(side, row, col, vectors);
    }

    public ArrayList<int[]> getBishopMoves(Side side, int row, int col) {
        int[][][] vectors =
            {{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        return findVectorMoves(side, row, col, vectors);
    }

    public ArrayList<int[]> getQueenMoves(Side side, int row, int col) {
        int[][][] vectors =
            {{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}},
            {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}},
            {{0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}},
            {{-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}},
            {{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}},
            {{-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}},
            {{1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}},
            {{-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}}};
        return findVectorMoves(side, row, col, vectors);
    }

    private ArrayList<int[]> findVectorMoves(Side side, int row, int col, int[][][] vectors) {
        ArrayList<int[]> moves = new ArrayList<>();
        for (int[][] subVectors: vectors) {
            for (int[] vector: subVectors) {
                int r = vector[0] + row;
                int c = vector[1] + col;
                if (isAvailable(side, r, c)) {
                    moves.add(new int[]{r, c});
                    if (board.getPiece(r, c) != '.') {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return moves;
    }

    public ArrayList<int[]> getPawnMoves(Side side, int row, int col) {
        int[][] takingVectors = new int[][]{{1, 1}, {1, -1}};
        int multiplier = getPawnSideMultiplier(side);
        ArrayList<int[]> moves = new ArrayList<>();
        if (board.getPiece(row + multiplier, col) == '.') {
            moves.add(new int[]{row + multiplier, col});
            if (board.getPiece(row + multiplier * 2, col) == '.' && (side == Side.WHITE && row == 1 || side == Side.BLACK && row == 6)) {
                moves.add(new int[]{row + multiplier * 2, col});
            }
        }
        for (int[] vector: takingVectors) {
            int r = vector[0] * multiplier + row;
            int c = vector[1] + col;
            Side targetSide = board.getPieceSide(board.getPiece(r, c));
            if (side != targetSide && targetSide != Side.NEUTRAL) {
                moves.add(new int[]{r, c});
            }
        }
        return moves;
    }

    public ArrayList<int[][]> getPromotionMoves(Side side, int col) {
        ArrayList<int[][]> moves = new ArrayList<>();
        if (side == Side.WHITE) {
            if (board.getPiece(7, col) == '.') {
                for (char option : board.getWhiteQueeningPieces()) {
                    moves.add(
                            new int[][]{{'.', 7, col}, {option, 8, col}}
                    );
                }
            }
        } else {
            if (board.getPiece(0, col) == '.') {
                for (char option : board.getBlackQueeningPieces()) {
                    moves.add(
                            new int[][]{{'.', 1, col}, {option, 0, col}}
                    );
                }
            }
        }
        return moves;
    }

    public ArrayList<int[][]> getCastlingMoves(Side side) {
        ArrayList<int[][]> moves = new ArrayList<>();
        if (side == Side.WHITE) {
            if (
                    board.getWhiteQueenSideCastling() &&
                    board.getPiece(0, 1) == '.' &&
                    board.getPiece(0, 2) == '.' &&
                    board.getPiece(0, 3) == '.'
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
                ) {
                moves.add(
                    new int[][]{{'.', 0, 0}, {'K', 0, 2}, {'R', 0, 3}, {'.', 0, 4}}
                );
            }
            if (
                    board.getWhiteKingSideCastling() &&
                    board.getPiece(0, 5) == '.' &&
                    board.getPiece(0, 6) == '.'
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
                ) {
                moves.add(
                    new int[][]{{'.', 0, 4}, {'K', 0, 6}, {'R', 0, 5}, {'.', 0, 7}}
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
                moves.add(
                    new int[][]{{'.', 7, 0}, {'k', 7, 2}, {'r', 7, 3}, {'.', 7, 4}}
                );
            }
            if (
                    board.getBlackKingSideCastling() &&
                    board.getPiece(7, 5) == '.' &&
                    board.getPiece(7, 6) == '.'
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
                ) {
                moves.add(
                    new int[][]{{'.', 7, 4}, {'k', 7, 6}, {'r', 7, 5}, {'.', 7, 7}}
                );
            }
        }
        return moves;
    }

    public ArrayList<int[][]> getEnPassantMoves(Side side) {
        ArrayList<int[][]> moves = new ArrayList<>();
        if (board.getEnPassantRow() != 0) {
            int r = board.getEnPassantRow();
            int c = board.getEnPassantCol();
            if (side == Side.WHITE) {
                if (isOnBoard(r - 1, c - 1) && board.getPiece(r - 1, c - 1) == 'P') {
                    moves.add(
                        new int[][]{{'P', r, c}, {'.', r - 1, c - 1}, {'.', r - 1, c}}
                    );
                }
                if (isOnBoard(r - 1, c + 1) && board.getPiece(r - 1, c + 1) == 'P') {
                    moves.add(
                        new int[][]{{'P', r, c}, {'.', r - 1, c + 1}, {'.', r - 1, c}}
                    );
                }
            } else {
                if (isOnBoard(r + 1, c - 1) && board.getPiece(r + 1, c - 1) == 'p') {
                    moves.add(
                        new int[][]{{'p', r, c}, {'.', r + 1, c - 1}, {'.', r + 1, c}}
                    );
                }
                if (isOnBoard(r - 1, c + 1) && board.getPiece(r + 1, c + 1) == 'p') {
                    moves.add(
                        new int[][]{{'p', r, c}, {'.', r + 1, c + 1}, {'.', r + 1, c}}
                    );
                }
            }
        }
        return moves;
    }

    private int getPawnSideMultiplier(Side side) {
        if (side == Side.WHITE) {
            return 1;
        }
        return -1;
    }
}
