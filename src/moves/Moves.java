package moves;

import board.Board;
import side.Side;

import java.util.ArrayList;

// TODO: 19.09.2017 Filter moves that leave king in check
// TODO: 19.09.2017 castling
// TODO: 19.09.2017 Bishop moves
// TODO: 19.09.2017 King moves (check checking!)
// TODO: 19.09.2017 Pawn moves (en passant!)
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
            default: throw new IllegalArgumentException();
        }
    }

    private boolean isOnBoard(int row, int col) {
        return !(row < 0 || row > 7 || col < 0 || col > 7);
    }

    public boolean isAvailable(Side side, int row, int col) {
        return isOnBoard(row, col) && side != board.getPieceSide(board.getPiece(row, col));
    }

    public ArrayList<int[]> getKnightMoves(Side side, int row, int col) {
        int[][] vectors = new int[][]{{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
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
        return null;
    }
}
