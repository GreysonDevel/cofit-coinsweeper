package eu.greyson.coinsweeper.game;

public class JumpOutOfFieldException extends RuntimeException {
    public JumpOutOfFieldException() {
        super("Player has jumped out of field.");
    }
}
