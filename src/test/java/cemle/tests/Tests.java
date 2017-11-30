package cemle.tests;

import cemle.board.Board;
import cemle.board.Squares;
import cemle.evaluation.Evaluation;
import cemle.moves.Moves;
import cemle.moves.NormalMove;
import cemle.moves.PawnDoubleMove;
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

    @Test
    public void sideToMoveTests() {
        assertEquals(board.getSideToMove(), 1);
        board.move(NormalMove.get(1, 4, 2, 4));
        assertEquals(board.getSideToMove(), -1);
        board.move(NormalMove.get(6, 4, 5, 4));
        assertEquals(board.getSideToMove(), 1);
        board.move(new PawnDoubleMove(Squares.get(1, 3), Squares.get(3, 3)));
        assertEquals(board.getSideToMove(), -1);
        board.printBoard();
    }

    @Test
    public void startBoardHasEqualMaterial() {
        evaluation = new Evaluation(board);
        assertEquals(evaluation.getBoardMaterialValue(), 0.0, 0.01);
    }

}
