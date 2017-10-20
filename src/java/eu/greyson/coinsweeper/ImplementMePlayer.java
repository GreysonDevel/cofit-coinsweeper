package eu.greyson.coinsweeper;  // do not change

import eu.greyson.coinsweeper.game.Game;
import eu.greyson.coinsweeper.game.Player;

public class ImplementMePlayer extends Player  {  // do not change

    public ImplementMePlayer(Game game) {
        super(game);
    }  // do not change

    /**
     * IMPLEMENT THIS METHOD
     *
     * Goal of the game is to get all coins with using minimal information - uncovering the least fields!
     *
     * Run "Launcher" to test your algorithm and view your score
     *
     * HOW TO PLAY:
     * Use game.gatherCoins() to gather all coins around the player (on 3x3 grid)
     * Use game.getVisibleCoins() to get all coins, that player currently sees (on 5x5 grid)
     * Use game.movePlayerTo(int x, int y) to move player on the field
     * Use game.getDistanceToWarden() to get distance to the warden
     *
     * GAME INFO:
     * game.getMinX() and game.getMinY() gives you the minimal X/Y coordinate on the field
     * game.getMaxX() and game.getMaxY() gives you the maximal X coordinate on X and Y axis respectively
     * game.getStartingAmountOfCoins() gives you staring amount of coins, scattered across the field
     * game.getDistance(Field f1, Field f2) helps you count the distance between two fields
     *
     * HELPER METHODS (player can use this methods to store information about discovered coins):
     *
     * print() - print string to the console (without jumping to new line)
     * println() - print string to the console and jump to next line
     *
     * Player store information:
     * markField(Field f, FieldState state) - mark field as either field with coin,
     *                                        empty field or unknown field (not yet discovered)
     *
     * Retrieve stored information:
     * getFieldState(Field f) - returns state of the field
     * isFieldEmpty/isFieldCoin/isFieldUnknown(Field f)
     * getEmptyFields/getCoinFields/getUnknownFields() - return all fields in the state
     *
     *
     */
    @Override
    public void findCoins() {
        // put your implementation here ...
    }
}
