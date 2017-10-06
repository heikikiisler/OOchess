package evaluation;

import board.Board;
import moves.Branch;
import moves.Move;
import moves.Moves;
import util.Config;

import java.util.ArrayList;
import java.util.List;


public class Evaluation {

    private Board board;
    public Moves moves;
    private int side;

    public Evaluation(Board board) {
        this.board = board;
        this.side = board.getSideToMove();
        this.moves = new Moves(board);
    }

    public Evaluation(Moves moves) {
        this.board = moves.board;
        this.side = board.getSideToMove();
        this.moves = moves;
    }

    public double getBoardTotalValue() {
        // TODO: 06.10.2017 Side multiplier for movesValues
        return getBoardMaterialValue() + board.getSideToMove() * getMovesValue();
    }

    private double getBoardMaterialValue() {
        double sum = 0;
        char[] pieces = board.getPieces();
        for (char piece: pieces) {
            if (piece != '.') {
                sum += Config.getPieceValue(piece);
            }
        }
        return sum;
    }

    private double getMovesValue() {
        // TODO: 06.10.2017 Also account for opponent moves
        double normal = Config.NORMAL_MULTIPLIER * moves.getAllPossibleMoves().size();
        double attack = Config.ATTACK_MULTIPLIER * moves.getAttackMoves().size();
        double defend = Config.DEFEND_MULTIPLIER * moves.getDefendMoves().size();
        return normal + attack + defend;
    }

    public Move getBestMove() {
        List<Move> possibleMoves = moves.getAllPossibleMoves();
        List<Branch> branches = new ArrayList<>();
        for (Move move: possibleMoves) {
            branches.add(new Branch(board, move, Config.DEPTH));
        }
        double bestValue = branches.get(0).getValue();
        Branch bestBranch = branches.get(0);
        for (Branch branch: branches) {
            double branchValue = branch.getValue();
            if (side == 1) {
                if (branchValue > bestValue) {
                    bestValue = branchValue;
                    bestBranch = branch;
                }
            } else {
                if (branchValue < bestValue) {
                    bestValue = branchValue;
                    bestBranch = branch;
                }
            }
        }
        return bestBranch.getMove();
    }



}
