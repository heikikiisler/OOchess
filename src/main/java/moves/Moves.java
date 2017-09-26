package moves;

import board.Board;

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

    public Board board;
    private ArrayList<int[]> moves;
    private ArrayList<int[]> attacked;
    private ArrayList<int[]> defended;
    private ArrayList<int[][]> specialMoves;

    public Moves(Board board) {
        this.board = board;
        this.moves = new ArrayList<>();
        this.attacked = new ArrayList<>();
        this.defended = new ArrayList<>();
        this.specialMoves = new ArrayList<>();
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
                getKingMoves(1, row, col);
            default: throw new IllegalArgumentException();
        }
    }

    public void getAllAvailableMoves(int side) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                char piece = board.getPiece(r, c);
                if (board.pieceIsSide(piece, side)) {
                    getAvailableMoves(r, c);
                }
            }
        }
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
                        moves.add(new int[]{row, col, r, c});
                    } else if (board.getPieceSide(targetPiece) != side) {
                        attacked.add(new int[]{row, col, r, c, targetPiece});
                        break;
                    } else {
                        defended.add(new int[]{row, col, r, c, targetPiece});
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
            moves.add(new int[]{row, col, row + side, col});
            if (board.getPiece(row + side * 2, col) == '.' && (side == 1 && row == 1 || side == -1 && row == 6)) {
                moves.add(new int[]{row, col, row + side * 2, col});
            }
        }
        getVectorMoves(side, row, col, takingVectors);
    }

    public void getPromotionMoves(int side, int col) {
        if (side == 1) {
            if (board.getPiece(7, col) == '.') {
                for (char option : board.getWhiteQueeningPieces()) {
                    specialMoves.add(
                        new int[][]{{'.', 7, col}, {option, 8, col}}
                    );
                }
            }
        } else {
            if (board.getPiece(0, col) == '.') {
                for (char option : board.getBlackQueeningPieces()) {
                    specialMoves.add(
                        new int[][]{{'.', 1, col}, {option, 0, col}}
                    );
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
                    new int[][]{{'.', 0, 0}, {'K', 0, 2}, {'R', 0, 3}, {'.', 0, 4}}
                );
            }
            if (
                    board.getWhiteKingSideCastling() &&
                    board.getPiece(0, 5) == '.' &&
                    board.getPiece(0, 6) == '.'
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
                ) {
                specialMoves.add(
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
                specialMoves.add(
                    new int[][]{{'.', 7, 0}, {'k', 7, 2}, {'r', 7, 3}, {'.', 7, 4}}
                );
            }
            if (
                    board.getBlackKingSideCastling() &&
                    board.getPiece(7, 5) == '.' &&
                    board.getPiece(7, 6) == '.'
                    // TODO: 20.09.2017 Add castling square check checking (check against list of attacked squares?)
                ) {
                specialMoves.add(
                    new int[][]{{'.', 7, 4}, {'k', 7, 6}, {'r', 7, 5}, {'.', 7, 7}}
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
                        new int[][]{{'P', r, c}, {'.', r - 1, c - 1}, {'.', r - 1, c}}
                    );
                }
                if (isOnBoard(r - 1, c + 1) && board.getPiece(r - 1, c + 1) == 'P') {
                    specialMoves.add(
                        new int[][]{{'P', r, c}, {'.', r - 1, c + 1}, {'.', r - 1, c}}
                    );
                }
            } else {
                if (isOnBoard(r + 1, c - 1) && board.getPiece(r + 1, c - 1) == 'p') {
                    specialMoves.add(
                        new int[][]{{'p', r, c}, {'.', r + 1, c - 1}, {'.', r + 1, c}}
                    );
                }
                if (isOnBoard(r - 1, c + 1) && board.getPiece(r + 1, c + 1) == 'p') {
                    specialMoves.add(
                        new int[][]{{'p', r, c}, {'.', r + 1, c + 1}, {'.', r + 1, c}}
                    );
                }
            }
        }
    }

    public ArrayList<int[]> getMoves() {
        return moves;
    }

    public ArrayList<int[]> getAttacked() {
        return attacked;
    }

    public ArrayList<int[]> getDefended() {
        return defended;
    }

    public ArrayList<int[][]> getSpecialMoves() {
        return specialMoves;
    }
}
