package evaluation;

import board.Board;
import com.esotericsoftware.kryo.Kryo;
import moves.Move;
import moves.NormalMove;
import moves.Moves;
import util.Conf;

import java.util.ArrayList;
import java.util.TreeMap;

// TODO: 20.09.2017 Evaluate piece position
// TODO: 20.09.2017 Evaluate board position
//      * Material difference
//      * Attacked pieces
//      * Defended pieces
//      * Number of available moves
// TODO: 20.09.2017 LATER: All-inclusive search tree with n-depth
// TODO: 20.09.2017 LATER: Pruning search tree
// TODO: 20.09.2017 MUCH LATER: Trying to optimize parameters with ML
public class Evaluation {

    public Moves moves;
    private Board board;
    private static final Kryo KRYO = new Kryo();

    public Evaluation(Moves moves) {
        this.moves = moves;
        this.board = moves.board;
    }

    public int getBoardMaterialValue(Board board) {
        int sum = 0;
        char[] pieces = board.getPieces();
        for (char piece: pieces) {
            if (piece != '.') {
                sum += Conf.getPieceValue(piece);
            }
        }
        return sum;
    }

    public double getMovesValue(Board board, int side) {
//        ArrayList<ArrayList<ArrayList<int[]>>> allMoves = moves.getAllAvailableMoves(side);
//        ArrayList<ArrayList<int[]>> moves = allMoves.get(0);
//        ArrayList<ArrayList<int[]>> attacked = allMoves.get(1);
//        ArrayList<ArrayList<int[]>> defended = allMoves.get(2);
//        // TODO: 23.09.2017 Actual evaluation
//        return moves.size() * 0.1 + + attacked.size() * 0.3 + defended.size() * 0.2;
        return 0.0;
    }

    public double getBoardTotalValue(Board board) {
        return getBoardMaterialValue(board) + getMovesValue(board, 1) - getMovesValue(board, -1);
    }

    public TreeMap<Double, Move> getSortedMoves(int side) {
        TreeMap<Double, Move> sortedMoves = new TreeMap<>();
        ArrayList<Move> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(moves.getNormalMoves());
        possibleMoves.addAll(moves.getAttackMoves());
        possibleMoves.addAll(moves.getSpecialMoves());
        for (Move move: possibleMoves) {
            sortedMoves.put(getTryMoveValue(move), move);
        }
        return sortedMoves;
    }

    public double getTryMoveValue(Move move) {
        Board copiedBoard = KRYO.copy(this.board);
        move.move(copiedBoard);
        return getBoardTotalValue(copiedBoard);
    }

}
