package cemle.evaluation;

import cemle.board.Board;
import cemle.moves.BranchOld;
import cemle.moves.Move;
import cemle.moves.Moves;
import cemle.util.Config;

import java.util.List;

public class Branching {

    public Move getBestMove(Board board) {
        Moves moves = new Moves(board);
        List<Move> possibleMoves = moves.getAllPossibleMoves();
        double bestValue = Double.MIN_VALUE;
        Move bestMove = null;
        for (Move move : possibleMoves) {
            Branch branch = new Branch(board, move, Config.DEPTH);
            double moveValue = branch.getValue();
            if (board.getSideToMove() == 1) {
                if (moveValue > bestValue) {
                    bestMove = move;
                    bestValue = moveValue;
                }
            } else {
                if (moveValue < bestValue) {
                    bestMove = move;
                    bestValue = moveValue;
                }
            }
        }
        return bestMove;
    }

}
