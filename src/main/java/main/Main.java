package main;

import board.Board;
import evaluation.Evaluation;
import game.Game;
import moves.Moves;
import util.Conf;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Moves moves = new Moves(board);
        Evaluation eval = new Evaluation(moves);
        Game game = new Game(eval);
        board.printBoard();
        Conf.getPieceValue('r');

//        board.move(6, 1, 4, 1);
//        board.move(4, 1, 3, 1);
//        board.move(1, 0, 3, 0);
//
//        //moves.getKnightMoves(1, 3, 3).forEach(m -> System.out.println(String.format("%d, %d", m[0], m[1])));
//        for (int[][] moveSet: moves.getEnPassantMoves(-1)) {
//            for (int[] m: moveSet) {
//                System.out.println(String.format("%c, %d, %d", (char)m[0], m[1], m[2]));
//            }
//        }
//        board.specialMove(moves.getEnPassantMoves(-1).get(0));
//
//        for (char piece:  new char[]{'p', 'r', 'n', 'b', 'q', 'k'}) {
//            System.out.println(String.format("%c: %d", piece, Conf.getPieceValue(piece)));
//        }

        for (int i = 0; i < 40; i++) {
            game.moveBestMove(1);
            game.moveBestMove(-1);
        }

    }
}
