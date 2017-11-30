package cemle.moves;

import cemle.board.Board;
import cemle.board.Square;

public class PromotionMove implements Move {

    private Square startSquare;
    private Square endSquare;
    private char piece;

    public PromotionMove(Square startSquare, Square endSquare, char piece) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.piece = piece;
    }

    @Override
    public void move(Board board) {
        board.promotionMove(startSquare, endSquare, piece);
    }

    public Square getStartSquare() {
        return startSquare;
    }

    public Square getEndSquare() {
        return endSquare;
    }

    public char getPiece() {
        return piece;
    }

}
