package main;

import board.Board;
import evaluation.Evaluation;
import moves.Move;
import moves.Moves;

import java.time.Duration;
import java.time.Instant;

public class Measuring {

    public static void main(String[] args) {
        Board board = new Board();
        Moves moves = new Moves(board);
        board.printBoard();
        Evaluation evaluation = new Evaluation(moves);

        //          123456789
        int loops = 500000000;
        //          1000000 - million
        for (int j = 0; j < 3; j++) {
            Instant start = Instant.now();
            for (int i = 0; i < loops; i++) {
                Move move2 = new Move(1, 4, 2, 4);
                board.move(move2);
            }
            Instant end = Instant.now();
            Instant start3 = Instant.now();
            for (int i = 0; i < loops; i++) {
                int[] move = new int[]{1, 4, 2, 4};
                board.move(move);
            }
            Instant end3 = Instant.now();
            System.out.println(Duration.between(start, end));
            System.out.println(Duration.between(start3, end3));
            System.out.println();
        }
    }

}
