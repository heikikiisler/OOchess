package cemle.game;

import cemle.board.Board;
import cemle.evaluation.Branch;
import cemle.evaluation.Evaluation;

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
        }
    }

}
