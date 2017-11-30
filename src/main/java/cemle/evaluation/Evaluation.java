package cemle.evaluation;

import cemle.board.Board;
import cemle.moves.Move;
import cemle.moves.Moves;
import cemle.util.Config;

import java.util.List;
import java.util.Random;


public class Evaluation {

    public Moves moves;
    private Board board;
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
        return board.getSideToMove() * getMovesValue() + getBoardMaterialValue();
    }

    public double getBoardMaterialValue() {
        double sum = 0;
        char[] pieces = board.getPieces();
        for (char piece : pieces) {
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

//    public Move getBestMove() {
//        List<Move> possibleMoves = moves.getAllPossibleMoves();
//        List<BranchOld> branches = new ArrayList<>();
//        for (Move move : possibleMoves) {
//            branches.add(new BranchOld(board, move, Config.DEPTH));
//        }
//        double bestValue = 0;
//        BranchOld bestBranch = null;
//        for (BranchOld branch : branches) {
//            double branchValue = branch.getValue();
//            if (bestBranch == null) {
//                bestBranch = branch;
//                bestValue = branchValue;
//            } else {
//                if (side == 1) {
//                    if (branchValue > bestValue) {
//                        bestValue = branchValue;
//                        bestBranch = branch;
//                    }
//                } else {
//                    if (branchValue < bestValue) {
//                        bestValue = branchValue;
//                        bestBranch = branch;
//                    }
//                }
//            }
//        }
//        System.out.println(String.format("best branchValue: %s", bestValue));
//        assert bestBranch != null;
//        return bestBranch.getMove();
//    }

    public Move getBestMove() {
        Moves moves = new Moves(board);
        List<Move> possibleMoves = moves.getAllPossibleMoves();
        double bestValue = Double.MIN_VALUE;
        Move bestMove = null;
        for (Move move : possibleMoves) {
            if (bestMove == null) {
                bestMove = move;
            }
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
        System.out.println("Best move value: " + bestValue);
        return bestMove;
    }

    public Move getRandomMove() {
        return moves.getAllPossibleMoves().get(new Random().nextInt(moves.getAllPossibleMoves().size()));
    }


}
