package main;

import board.Board;
import board.Square;
import board.Squares;
import evaluation.Evaluation;
import moves.Branch;
import moves.Move;
import moves.Moves;
import moves.NormalMove;
import util.Config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Evaluation evaluation = new Evaluation(board);
        Branch branch = new Branch(board, new NormalMove(Squares.get(0, 6), Squares.get(2, 5)), 2);
        board.printBoard();
        Move bestMove = evaluation.getBestMove();
        bestMove.move(board);
        board.printBoard();
//        board.move(new NormalMove(Squares.get(1, 4), Squares.get(2, 4)));
//        board.printBoard();
//        board.move(new NormalMove(Squares.get(6, 4), Squares.get(5, 4)));
//        board.printBoard();
//        board.move(new NormalMove(Squares.get(0, 1), Squares.get(2, 2)));
//        board.printBoard();
//        board.move(new NormalMove(Squares.get(7, 1), Squares.get(5, 2)));
//        board.printBoard();
    }
}
