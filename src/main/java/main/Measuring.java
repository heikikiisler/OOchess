package main;

import board.Board;
import evaluation.Evaluation;
import moves.Moves;

import java.time.Duration;
import java.time.Instant;

public class Measuring {

    public static void main(String[] args) {
        Moves moves = new Moves(new Board());
        moves.board.printBoard();
        Evaluation evaluation = new Evaluation(moves);

        //          123456789
        int loops = 10000000;
        //          1000000 - million
        for (int j = 0; j < 3; j++) {
            Instant start = Instant.now();
            for (int i = 0; i < loops; i++) {
//                evaluation.getBoardMaterialValue();
            }
            Instant end = Instant.now();
            Instant start3 = Instant.now();
            for (int i = 0; i < loops; i++) {
//                evaluation.getBoardMaterialValue();
            }
            Instant end3 = Instant.now();
            System.out.println(Duration.between(start, end));
            System.out.println(Duration.between(start3, end3));
            System.out.println();
        }
    }

}
