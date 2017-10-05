package evaluation;

import board.Board;
import moves.Move;
import moves.Moves;
import util.Config;

import java.util.ArrayList;
import java.util.List;
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
                sum += Config.getPieceValue(piece);
            }
        }
        return sum;
    }


    private double getBoardTotalValue(Board board) {
        return getBoardMaterialValue(board);
    }

    public TreeMap<Double, Move> getPlySortedMoves(int side) {
        TreeMap<Double, Move> sortedMoves = new TreeMap<>();
        List<Move> possibleMoves = moves.getAllPossibleMoves();
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
