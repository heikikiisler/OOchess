package evaluation;

import board.Board;
import com.esotericsoftware.kryo.Kryo;
import moves.Moves;
import util.Conf;

import java.util.ArrayList;

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
    private Kryo kryo;

    public Evaluation(Moves moves) {
        this.moves = moves;
        this.board = moves.board;
        this.kryo = new Kryo();
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
        ArrayList<ArrayList<ArrayList<int[]>>> allMoves = moves.getAllAvailableMoves(side);
        ArrayList<ArrayList<int[]>> moves = allMoves.get(0);
        ArrayList<ArrayList<int[]>> attacked = allMoves.get(1);
        ArrayList<ArrayList<int[]>> defended = allMoves.get(2);
        // TODO: 23.09.2017 Actual evaluation
        return moves.size() * 0.1 + + attacked.size() * 0.3 + defended.size() * 0.2;
    }

    public double getBoardTotalValue(Board board) {
        return getBoardMaterialValue(board) + getMovesValue(board, 1) - getMovesValue(board, -1);
    }

    public int[][] getBestMove(int side) {
        ArrayList<ArrayList<ArrayList<int[]>>> allMoves = moves.getAllAvailableMoves(side);
        double highestValue = -100;
        int[][] bestMove = new int[0][];
        for (ArrayList<ArrayList<int[]>> pieceMoves: allMoves) {
            int[] start = pieceMoves.get(0).get(0);
            ArrayList<int[]> movableMoves = new ArrayList<int[]>(){{addAll(pieceMoves.get(1)); addAll(pieceMoves.get(2));}};
            for (int[] move: movableMoves) {
                double tryMoveValue = getTryMoveValue(start, move);
                if (tryMoveValue * side > highestValue) {
                    highestValue = tryMoveValue * side;
                    bestMove = new int[][]{start, move};
                }
            }
        }
        return bestMove;
    }

    public double getTryMoveValue(int[] start, int[] end) {
        Board board = kryo.copy(this.board);
        board.move(start[0], start[1], end[0], end[1]);
        return getBoardTotalValue(board);
    }





}
