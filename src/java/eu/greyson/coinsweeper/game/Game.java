package eu.greyson.coinsweeper.game;

import java.util.*;


public final class Game {

    /**
     * Number of first position in the game plan (on X or Y axis).
     */
    static int MIN_POSITION = 1;

    /**
     * Player step distance.
     */
    private static int STEP_DISTANCE = 1;

    /**
     * Player jump distance.
     */
    private static int JUMP_DISTANCE = 4;

    /**
     * How far warden sees.
     */
    private static int WARDEN_SPOT_DISTANCE = 1;

    private final GardenPlan gardenPlan;
    private final Score score;
    private final Set<Field> gatheredCoins = new HashSet<>();
    private final Date created;

    /**
     * Player state
     */
    private Field playerPosition = new Field(MIN_POSITION, MIN_POSITION);


    Game(GardenPlan gardenPlan) {
        this.gardenPlan = gardenPlan;
        this.score = new Score(gardenPlan);
        this.created = new Date();
    }

    /**
     * Gather all coins on 3x3 grid around player (assuming player is in the middle of this grid).
     * There is a penalty on resulting score for performing this action
     * and a score bonus for each gathered coin. Penalty is much higher that coin bonus,
     * so it's best not to call this method too often, but only when there is a bigger amount of
     * coins scattered around the player.
     *
     * @return number of gathered coins
     */
    public int gatherCoins() throws TimeoutException {

        checkTimeout();

        Set<Field> coinsToTake = gardenPlan.getReadyToGatherCoins(getPlayerPosition());

        int coinsTaken = 0;

        for (Field coin : coinsToTake) {

            if (!gatheredCoins.contains(coin)) {

                gatheredCoins.add(coin);
                score.incGatheredCoins();
                coinsTaken++;
            }
        }

        score.incGroundTouched();

        return coinsTaken;
    }

    /**
     * Returns distance to the warden. (calculated as a sum of absolute distances along both X and Y axes)
     * EXAMPLE: if player is at position(1,1) and warden is at (3,3), then distance will be calculated as:
     * abs(p.x-w.x) + abs(p.y-w.y) = abs(3-1) + (3-1)abs = 2 + 2 = 4
     */
    public int getDistanceToWarden() {
        return getDistance(getPlayerPosition(), gardenPlan.getWarden().getWardenPosition());
    }

    /**
     * Moves player to the new position of the field.
     * Player can only perform either single step (distance = 1) or jump (distance = 4). If player
     * tries to step/jump out of field or chooses the wrong distance, method immediately throws an exception.
     * Player is free to catch this exception and correct its move distance. Player can also be spotted by a warden,
     * walking across the garden, if it happens, the game ends with 0 score.
     *
     * @throws JumpOutOfFieldException if player tries to jump out of field
     * @throws WrongMoveDistanceException if player performs move with wrong distance (neither jump nor single step)
     * @throws CaughtByWardenException thrown when player jumps to the position where warden is staying
     * @throws SpottedByWardenException thrown when player is spotted by the warden
     */
    public void movePlayerTo(int x, int y) throws JumpOutOfFieldException,
                                                  WrongMoveDistanceException,
                                                  CaughtByWardenException,
                                                  SpottedByWardenException,
                                                  TimeoutException {

        checkTimeout();

        Field newPosition = new Field(x, y);
        int distance = GardenPlan.getDistance(playerPosition, newPosition);

        if (!(distance == STEP_DISTANCE || distance == JUMP_DISTANCE)) {
            throw new WrongMoveDistanceException();
        }

        if (x < getMinX() || x > gardenPlan.getMaxX() ||
            y < getMinY() || y > gardenPlan.getMaxY()) {

            throw new JumpOutOfFieldException();
        }

        // update player position
        playerPosition = newPosition;

        // increment number of moves
        score.incMoves();

        // update warden position
        gardenPlan.getWarden().move();

        // get current warden position
        Field wardenPosition = gardenPlan.getWarden().getWardenPosition();

        // get distance to warden
        int distanceToWarden = GardenPlan.getDistance(playerPosition, wardenPosition);

        // check if player jumped to the warden field
        if (distanceToWarden == 0) {

            score.catchedByWarden();
            throw new CaughtByWardenException();
        }

        // check if warden sees the player
        if (Math.abs(wardenPosition.getX() - playerPosition.getX()) <= WARDEN_SPOT_DISTANCE &&
            Math.abs(wardenPosition.getY() - playerPosition.getY()) <= WARDEN_SPOT_DISTANCE) {

            score.spottedByWarden();
            throw new SpottedByWardenException();
        }
    }

    /**
     * Returns current position of the player on the field.
     */
    public Field getPlayerPosition() {
        return playerPosition;
    }

    /**
     * Returns visible coins around the player
     * (on the distance of 3, i.e. 5x5 grid around the player, assuming player is in the middle of this grid).
     */
    public Set<Field> getVisibleCoins() {

        Field playerPosition = getPlayerPosition();

        return gardenPlan.getVisibleCoins(playerPosition);
    }

    /**
     * Get player's actual score
     * @return Player's score
     */
    public ScoreResult getScoreResult() throws TimeoutException {
        checkTimeout();
        return new ScoreResult(score);
    }

    /**
     * Returns minimal valid position on X axis
     * @return Minimal valid position on X axis
     */
    public int getMinX() throws TimeoutException {
        checkTimeout();
        return MIN_POSITION;
    }

    /**
     * Returns minimal valid position on Y axis
     * @return Minimal valid position on Y axis
     */
    public int getMinY() throws TimeoutException {
        checkTimeout();
        return MIN_POSITION;
    }

    /**
     * Returns maximal valid position on X axis
     * @return Maximal valid position on X axis
     */
    public int getMaxX() throws TimeoutException {
        checkTimeout();
        return gardenPlan.getMaxX();
    }

    /**
     * Returns maximal valid position on Y axis
     * @return Maximal valid position on Y axis
     */
    public int getMaxY() throws TimeoutException {
        checkTimeout();
        return gardenPlan.getMaxY();
    }

    /**
     * Returns starting amount of coins
     */
    public int getStartingAmountOfCoins() throws TimeoutException {
        checkTimeout();
        return gardenPlan.getNumberOfCoines();
    }

    /**
     * Calculate the distance between two positions
     * @param from Position to count distance from
     * @param to Position to count distance to
     * @return Distance between the two positions
     */
    public int getDistance(Field from, Field to) throws TimeoutException {
        checkTimeout();
        return GardenPlan.getDistance(from, to);
    }

    /** Check if max time to execute is exceeded. */
    private void checkTimeout() throws TimeoutException {
        if((new Date().getTime() - created.getTime()) > 30000) {
            throw new TimeoutException();
        }
    }
}
