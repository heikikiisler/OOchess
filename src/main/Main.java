package main;

import board.Board;
import moves.Moves;
import side.Side;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Moves moves = new Moves(board);
        board.printBoard();

        board.move(6, 1, 4, 1);
        board.move(4, 1, 3, 1);
        board.move(1, 0, 3, 0);

        //moves.getKnightMoves(Side.WHITE, 3, 3).forEach(m -> System.out.println(String.format("%d, %d", m[0], m[1])));
        for (int[][] moveSet: moves.getEnPassantMoves(Side.BLACK)) {
            for (int[] m: moveSet) {
                System.out.println(String.format("%c, %d, %d", (char)m[0], m[1], m[2]));
            }
        }
        board.specialMove(moves.getEnPassantMoves(Side.BLACK).get(0));
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                System.out.print(String.format("{%d, %d}, ", i, j));
            }
        }

    }
}
