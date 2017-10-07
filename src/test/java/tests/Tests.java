package tests;

import board.Board;
import board.Square;
import board.Squares;
import evaluation.Evaluation;
import moves.Move;
import moves.Moves;
import moves.NormalMove;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class Tests {

    private Board board;
    private Moves moves;
    private Evaluation evaluation;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void boardInitialStateTest() {
        assertEquals(board.getSideToMove(), 1);
        assertEquals(board.getEnPassantSquare(), null);
    }

    @Test
    public void whiteHas20MovesWithInitialBoard() {
        moves = new Moves(board);
        assertEquals(moves.getAllPossibleMoves().size(), 20);
    }

    @Test
    public void pushedPushedPawnsCorrectMovesTotal() {
        for (int i = 0; i < 8; i++) {
            board.move(new NormalMove(Squares.get(1, i), Squares.get(3, i)));
            board.move(new NormalMove(Squares.get(6, i), Squares.get(4, i)));
        }
        moves = new Moves(board);
        assertEquals(moves.getAllPossibleMoves().size(), 41);
        board.move(new NormalMove(Squares.get(0, 4), Squares.get(1, 4)));
        assertEquals(moves.getAllPossibleMoves().size(), 41);
    }
}
