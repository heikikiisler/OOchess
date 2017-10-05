package main;

import board.Board;
import board.Square;
import board.Squares;
import evaluation.Evaluation;
import moves.Move;
import moves.Moves;
import util.Config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();
        Evaluation evaluation = new Evaluation(board);
        Moves moves = new Moves(board);
        System.out.println(moves.getAllPossibleMoves().size());
        System.out.println(moves.getNormalMoves().size());
        System.out.println(moves.getAttackMoves().size());
        System.out.println(moves.getSpecialMoves().size());
        System.out.println(moves.getDefendMoves().size());
        System.out.println(moves.getAttackedSquares().size());
    }
}
