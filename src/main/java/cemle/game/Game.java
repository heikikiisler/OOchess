package cemle.game;

import cemle.board.Board;
import cemle.board.Square;
import cemle.board.Squares;
import cemle.evaluation.Branch;
import cemle.evaluation.Evaluation;
import cemle.moves.NormalMove;

public class Game {

    private Board board;

    public Game() {
        this.board = new Board();
        board.printBoard();
    }

    public void moveBestMove() {
        Evaluation evaluation = new Evaluation(board);
        evaluation.getBestMove().move(board);
    }

    public void playAgainstItself(int numberOfPlies) {
        for (int i = 0; i < numberOfPlies; i++) {
            moveBestMove();
            board.printBoard();
            System.out.println(Branch.branchDepthInitializations.entrySet());
        }
    }

}
