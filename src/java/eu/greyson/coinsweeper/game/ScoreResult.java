package eu.greyson.coinsweeper.game;

public class ScoreResult {
    private Score score;

    ScoreResult(Score score) {
        this.score = score;
    }

    public int getWrongGuesses() {
        return score.getGroundTouched();
    }

    public int getRevealedMines() {
        return score.getGatheredCoins();
    }

    public int getUncoveredFields() {
        return score.getMoves();
    }

    public int getNumberOfMines() {
        return score.getStartingNumberOfCoins();
    }

    public long getScore() {
        return score.getScore();
    }

    public void printScore() {

        System.out.println("-----------------------------------");
        System.out.println("YOUR SCORE:");
        System.out.println("Score: " + score.getScore());

        System.out.println("Gathered coins: " + score.getGatheredCoins() + "/" + score.getStartingNumberOfCoins());
        System.out.println("Moves performed: " + score.getMoves());
        System.out.println("How many times player performed gathering: " + score.getGroundTouched());
    }
}
