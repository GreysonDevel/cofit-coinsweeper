package eu.greyson.coinsweeper.game;

public class WrongMoveDistanceException extends RuntimeException {
    public WrongMoveDistanceException() {
        super("Bad move distance, not step(1) nor jump(4)!");
    }
}
