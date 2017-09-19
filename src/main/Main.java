package main;

import board.Board;
import piece.Pawn;
import side.Side;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        Pawn pawn = new Pawn(Side.WHITE, 1, 1);
    }
}
