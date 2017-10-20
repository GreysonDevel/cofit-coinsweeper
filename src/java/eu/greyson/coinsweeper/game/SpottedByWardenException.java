package eu.greyson.coinsweeper.game;

public class SpottedByWardenException extends RuntimeException {
    public SpottedByWardenException() {
        super("Player was spotted by warden!");
    }
}
