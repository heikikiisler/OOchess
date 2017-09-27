package evaluation;

import board.Board;
import com.esotericsoftware.kryo.Kryo;
import moves.Move;
import moves.NormalMove;
import moves.Moves;
import util.Conf;

import java.util.ArrayList;
import java.util.TreeMap;


public class Evaluation {

    private Board board;
    public Moves moves;

    public Evaluation(Board board) {
        this.board = board;
        this.moves = new Moves(board);
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


    private double getBoardTotalValue(Board board) {
        return getBoardMaterialValue(board);
    }

    public TreeMap<Double, Move> getPlySortedMoves(int side) {
        TreeMap<Double, Move> sortedMoves = new TreeMap<>();
        ArrayList<Move> possibleMoves = moves.getAllPossibleMoves();
        for (Move move: possibleMoves) {
            sortedMoves.put(getTryMoveValue(move), move);
        }
        return sortedMoves;
    }

    private double getTryMoveValue(Move move) {
        Board copiedBoard = board.getCopy();
        move.move(copiedBoard);
        return getBoardTotalValue(copiedBoard);
    }



}
