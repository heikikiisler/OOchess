package cemle.main;

import cemle.game.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.playAgainstItself(40);
//        Board board = new Board();
//        for (int i = 0; i < 40; i++) {
//            Evaluation evaluation = new Evaluation(board);
//            board.move(evaluation.getBestMove());
//            board.printBoard();
//
//        }
//        System.out.println(Branch.map.entrySet());
//        System.out.println(Square.count);
    }
}
