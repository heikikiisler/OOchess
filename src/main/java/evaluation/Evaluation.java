package evaluation;

import board.Board;
import moves.Moves;

// TODO: 20.09.2017 Evaluate piece position
// TODO: 20.09.2017 Evaluate board position
//      * Material difference
//      * Attacked pieces
//      * Defended pieces
//      * Number of available moves
//      *
// TODO: 20.09.2017 LATER: All-inclusive search tree with n-depth
// TODO: 20.09.2017 LATER: Pruning search tree
// TODO: 20.09.2017 LATER: Evaluating parameters to config file
// TODO: 20.09.2017 MUCH LATER: Trying to optimize parameters with ML
public class Evaluation {

    private Moves moves;
    private Board board;

    public Evaluation(Moves moves) {
        this.moves = moves;
        this.board = moves.board;
    }

    public int getBoardMaterialValue() {

        return 0;
    }
}
