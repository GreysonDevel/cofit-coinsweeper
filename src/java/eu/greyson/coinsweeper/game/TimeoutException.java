package eu.greyson.coinsweeper.game;

/** Max time to execute is exceeded. */
public class TimeoutException extends RuntimeException {
    public TimeoutException() {
        super("Vypršel časový limit");
    }
}
