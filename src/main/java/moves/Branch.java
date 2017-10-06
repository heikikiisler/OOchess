package moves;

import board.Board;
import board.Square;
import evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Branch {

    private ArrayList<Branch> branches = new ArrayList<>();
    private Board board;
    private Move move;
    private Moves moves;
    private Branch parent;
    private int side;
    private int depth;
    private boolean alive = true;

    public Branch(Board board, Move move, int depth) {
        this.board = board.getCopy();
        this.move = move;
        this.depth = depth;
        this.side = board.getSideToMove();
        move.move(this.board);
        this.moves = new Moves(board);
        if (checkForCheck()) {
            findNewBranches();
        }
    }

    private Branch getSubBranch(Branch parent, Move move) {
        Branch branch = new Branch(parent.board, move, this.depth - 1);
        branch.parent = parent;
        return branch;
    }

    private boolean checkForCheck() {
        Set<Square> checkSquares = moves.getAttackedSquares();
        for (Square disallowedSquare: board.getDisallowedCheckSquares()) {
            for (Square checkSquare: checkSquares) {
                if (checkSquare.getIndex() == disallowedSquare.getIndex()) {
                    if (parent != null) {
                        parent.die();
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void findNewBranches() {
        if (depth > 0) {
            List<Move> counterMoves = moves.getAllPossibleMoves();
            for (Move move : counterMoves) {
                if (alive) {
                    branches.add(getSubBranch(this, move));
                } else {
                    return;
                }
            }
        }
    }

    private void die() {
        alive = false;
        parent.killBranch(this);
    }

    private synchronized void killBranch(Branch childBranch) {
        branches.remove(childBranch);
    }

    public double getValue() {
        if (branches.isEmpty()) {
            return new Evaluation(moves).getBoardTotalValue();
        }
        double bestValue = branches.get(0).getValue();
        for (Branch branch: branches) {
            double branchValue = branch.getValue();
            if (side == 1) {
                if (branchValue > bestValue) {
                    bestValue = branchValue;
                }
            } else {
                if (branchValue < bestValue) {
                    bestValue = branchValue;
                }
            }
        }
        return bestValue;
    }

    public Move getMove() {
        return move;
    }

}
