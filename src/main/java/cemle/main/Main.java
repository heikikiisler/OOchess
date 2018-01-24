package cemle.main;

import cemle.game.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.playAgainstItself(40);
    }
}
