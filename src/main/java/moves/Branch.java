package moves;

import board.Board;
import board.Square;

import java.util.ArrayList;
import java.util.Set;

public class Branch {

    private ArrayList<Branch> branches;
    private Board board;
    private Moves moves;
    private Move move;

    public Branch(Board board, Move move, int depth) {
        this.board = board;
        this.move = move;
        move.move(this.board);
        this.moves = new Moves(board);
        checkForCheck();
    }

    private void checkForCheck() {
        if (move.getType() != MoveType.CASTLING) {
            Set<Square> checkSquares = moves.getAttackedSquares();
            Square kingSquare = board.getKingPosition(board.getSideToMove());
            for (Square square: checkSquares) {
                if (square.getIndex() == kingSquare.getIndex()) {
                    // TODO: 27.09.2017 parentBranch.kill(value)
                }
            }
        } else {

        }
    }

    public void findNewBranches(int depth) {
        branches = new ArrayList<>();
        ArrayList<Move> counterMoves = moves.getAllPossibleMoves();
        for (Move move: counterMoves) {
            branches.add(new Branch(board.getCopy(), move, depth - 1));
        }
    }

}
