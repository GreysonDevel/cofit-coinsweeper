package eu.greyson.coinsweeper.game;


public class CaughtByWardenException extends RuntimeException {
    public CaughtByWardenException(){
        super("Player was caught by warden!");
    }
}
