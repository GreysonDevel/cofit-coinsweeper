package eu.greyson.coinsweeper;

import eu.greyson.coinsweeper.game.*;
import eu.greyson.coinsweeper.game.InitGame;
import eu.greyson.coinsweeper.game.Game;

import java.io.IOException;

public class Launcher {
    /** You can pass map path to play. */
    public static void main(String[] arg) throws TimeoutException {
        String map = arg.length == 0 ? "map.txt" : arg[0];

        try {
            Game game = InitGame.loadGame(map);
            Player player = new ImplementMePlayer(game);
            player.findCoins();

            game.getScoreResult().printScore();
        } catch (IOException e) {
            System.out.println("Map not found.");
        }
    }
}
