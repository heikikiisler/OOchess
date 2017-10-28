package game;

import board.Board;
import board.Square;
import evaluation.Evaluation;
import moves.Branch;

public class Game {

    private Board board;

    public Game() {
        this.board = new Board();
    }

    public void moveBestMove() {
        Evaluation evaluation = new Evaluation(board);
        evaluation.getBestMove().move(board);
    }

    public void playAgainstItself(int numberOfPlies) {
        for (int i = 0; i < numberOfPlies; i++) {
            moveBestMove();
            board.printBoard();
            System.out.println(Branch.map.entrySet());
            System.out.println(Square.count);
        }
    }

}
