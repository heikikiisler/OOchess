package main;

import board.Board;
import board.Square;
import evaluation.Evaluation;
import game.Game;
import moves.Branch;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.playAgainstItself(40);
//        Board board = new Board();
//        Evaluation evaluation = new Evaluation(board);
//        board.move(evaluation.getBestMove());
//        board.printBoard();
//        System.out.println(Branch.map.entrySet());
//        System.out.println(Square.count);
    }
}
