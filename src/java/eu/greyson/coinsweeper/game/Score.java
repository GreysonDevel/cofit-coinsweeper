package eu.greyson.coinsweeper.game;

class Score {

    private static int SCORE_GROUND_TOUCHED = 10;
    private static int SCORE_MOVE_PERFORMED = 1;
    private static long SCORE_COEFFICIENT = 20;

    private GardenPlan plan;

    private int groundTouched = 0;
    private int gatheredCoins = 0;
    private int moves = 0;

    private boolean spottedByWarden = false;
    private boolean catchedByWarden = false;

    Score(GardenPlan plan) {
        this.plan = plan;
    }

    void incGroundTouched() {
        if (groundTouched < Integer.MAX_VALUE) {
            groundTouched++;
        }
    }

    void incGatheredCoins() {
        gatheredCoins++;
    }

    void incMoves() {
        if (moves < Integer.MAX_VALUE) {
            moves++;
        }
    }

    void spottedByWarden() {
        spottedByWarden = true;
    }

    void catchedByWarden() {
        catchedByWarden = true;
    }

    int getGroundTouched() {
        return groundTouched;
    }

    int getGatheredCoins() {
        return gatheredCoins;
    }

    int getMoves() {
        return moves;
    }

    int getStartingNumberOfCoins() {
        return plan.getNumberOfCoines();
    }

    long getMaxScore() {
        return SCORE_COEFFICIENT * (plan.getMaxX() + plan.getMaxY());
    }

    long getScore() {

        if (spottedByWarden || catchedByWarden) {
            return 0;
        }

        long score = (long)((getMaxScore() - SCORE_MOVE_PERFORMED * moves - SCORE_GROUND_TOUCHED * groundTouched)
                * (gatheredCoins * 1.0 / plan.getNumberOfCoines()));
        int zeroScore = ((getGatheredCoins() == getStartingNumberOfCoins()) ? gatheredCoins : 0);
        return Math.max(zeroScore, score);
    }
}
