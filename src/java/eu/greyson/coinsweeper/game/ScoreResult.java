package eu.greyson.coinsweeper.game;

public class ScoreResult {
    private Score score;

    ScoreResult(Score score) {
        this.score = score;
    }

    public int getGroundTouched() {
        return score.getGroundTouched();
    }

    public int getGatheredCoins() {
        return score.getGatheredCoins();
    }

    public int getMoves() {
        return score.getMoves();
    }

    public int getStartingNumberOfCoins() {
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
