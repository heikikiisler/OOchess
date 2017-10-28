package moves;

import board.Board;
import board.Square;
import evaluation.Evaluation;

import java.util.*;

public class Branch {

    public static Map<Integer, Integer> map = new HashMap<>();

    static {
        map.put(0, 0);
        map.put(1, 0);
        map.put(2, 0);
        map.put(3, 0);
    }

    private ArrayList<Square> disallowedCheckSquares;
    private ArrayList<Branch> branches = new ArrayList<>();
    private Board board;
    private Move move;
    private Moves moves;
    private Branch parent;
    private int side;
    private int depth;
    private boolean alive = true;

    public Branch(Board board, Move move, int depth) {
        map.put(depth, map.get(depth) + 1);
        this.board = board.getCopy();
        this.move = move;
        this.depth = depth;
        this.disallowedCheckSquares = board.getDisallowedCheckSquares();
        move.move(this.board);
        this.side = this.board.getSideToMove();
        this.moves = new Moves(this.board);
        if (checkForCheck()) {
            findNewBranches();
        }
    }

    private Branch(Branch parent, Move move) {
        this.parent = parent;
        this.board = parent.board.getCopy();
        this.move = move;
        this.depth = parent.depth - 1;
        map.put(depth, map.get(depth) + 1);
        this.disallowedCheckSquares = board.getDisallowedCheckSquares();
        move.move(this.board);
        this.side = board.getSideToMove();
        this.moves = new Moves(board);
        if (checkForCheck()) {
            findNewBranches();
        }
    }

    private boolean checkForCheck() {
        Set<Square> checkSquares = moves.getAttackedSquares();
        for (Square disallowedSquare : disallowedCheckSquares) {
            for (Square checkSquare : checkSquares) {
                if (checkSquare.getIndex() == disallowedSquare.getIndex()) {
                    if (parent != null) {
                        parent.die();
                        alive = false;
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
                    branches.add(new Branch(this, move));
                } else {
                    return;
                }
            }
        }
    }

    private void die() {
        alive = false;
        if (parent != null) {
            parent.killBranch(this);
        }
    }

    private synchronized void killBranch(Branch childBranch) {
        branches.remove(childBranch);
    }

    public double getValue() {
        if (branches.isEmpty()) {
            return new Evaluation(moves).getBoardTotalValue();
        }
        boolean valueSet = false;
        double bestValue = 0;
        for (Branch branch : branches) {
            double branchValue = branch.getValue();
            if (!valueSet) {
                bestValue = branchValue;
                valueSet = true;
            } else {
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
        }
        return bestValue;
    }

    public Move getMove() {
        return move;
    }

}
