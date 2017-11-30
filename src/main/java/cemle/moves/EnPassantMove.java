package cemle.moves;

import cemle.board.Board;
import cemle.board.Square;

public class EnPassantMove implements Move {

    private Square startSquare;

    public EnPassantMove(Square startSquare) {
        this.startSquare = startSquare;
    }

    @Override
    public void move(Board board) {
//        board.printBoard();
//        System.out.println(String.format("En passant square: %d, %d", board.getEnPassantSquare().getRow(), board.getEnPassantSquare().getCol()));
        board.enPassantMove(startSquare);
    }
}
