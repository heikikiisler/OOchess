package game;

import board.Board;
import evaluation.Evaluation;
import moves.Moves;

import java.util.Arrays;

public class Game {

    private Board board;
    private Moves moves;
    private Evaluation evaluation;

    public Game(Evaluation evaluation) {
        this.evaluation = evaluation;
        this.moves = evaluation.moves;
        this.board = moves.board;
    }

    public void moveBestMove(int side) {
        int[][] bestMove = evaluation.getBestMove(side);
        System.out.println(Arrays.deepToString(bestMove));
        board.move(bestMove);
        board.printBoard();
    }

}
