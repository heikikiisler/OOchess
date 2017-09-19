package main;

import board.Board;
import moves.Moves;
import side.Side;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        Moves moves = new Moves(board);
        //moves.getKnightMoves(Side.WHITE, 3, 3).forEach(m -> System.out.println(String.format("%d, %d", m[0], m[1])));

        for (int i = 0; i < 8; i++) {
            //System.out.print(String.format("{%d, 0}, ", -i));
            //System.out.print(String.format("{0, %d}, ", -i));
        }
        //moves.getBishopMoves(Side.WHITE, 3, 3).forEach(m -> System.out.println(String.format("%d, %d", m[0], m[1])));
        System.out.println();
        moves.getQueenMoves(Side.WHITE, 0, 3).forEach(m -> System.out.println(String.format("%d, %d", m[0], m[1])));

    }
}
