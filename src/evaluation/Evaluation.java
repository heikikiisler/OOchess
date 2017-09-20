package evaluation;

import board.Board;
import moves.Moves;

// TODO: 20.09.2017 Evaluate piece position
// TODO: 20.09.2017 Evaluate board position
// TODO: 20.09.2017 LATER: All-inclusive search tree with n-depth
// TODO: 20.09.2017 LATER: Pruning search tree
// TODO: 20.09.2017 LATER: Evaluating parameters to config file
// TODO: 20.09.2017 MUCH LATER: Trying to optimize parameters with ML
public class Evaluation {

    private Board board;
    private Moves moves;

    public Evaluation(Board board) {
        this.board = board;
        this.moves = new Moves(board);
    }
}
