package eu.greyson.coinsweeper.game;

import java.util.*;

final class GardenPlan {

    private final static int PLAYER_VIEW_DISTANCE = 2;
    private final static int PLAYER_GRAB_DISTANCE = 1;

    private final int maxX;
    private final int maxY;
    private final Set<Field> coins;
    private final Warden warden;

    /**
     *
     * @param maxX Maximal valid X position on the plan
     * @param maxY Maximal valid Y position on the plan
     * @param coins Set of all coins
     */
    GardenPlan(int maxX, int maxY, Set<Field> coins, Warden warden) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.coins = coins;
        this.warden = warden;
    }

    /**
     * Returns all nearby coins, based on player's view distance
     */
    Set<Field> getVisibleCoins(Field playerPosition) {
        return getNearbyCoins(playerPosition, PLAYER_VIEW_DISTANCE);
    }

    /**
     * Returns all nearby coins, that are possible to gather by player
     */
    Set<Field> getReadyToGatherCoins(Field playerPosition) {
        return getNearbyCoins(playerPosition, PLAYER_GRAB_DISTANCE);
    }

    /**
     * Returns all nearby coins, filtered by distance
     */
    private Set<Field> getNearbyCoins(Field playerPosition, int distance) {

        Set<Field> result = new HashSet<>();

        for (Field coin : coins) {

            if (Math.abs(coin.getX() - playerPosition.getX()) <= distance &&
                Math.abs(coin.getY() - playerPosition.getY()) <= distance) {

                result.add(coin);
            }
        }
        return result;
    }

    Warden getWarden() {
        return warden;
    }

    /**
     * Get maximal valid X position on the plan
     * @return Maximal valid X position on the plan
     */
    int getMaxX() {
        return maxX;
    }

    /**
     * Get maximal valid Y position on the plan
     * @return Maximal valid Y position on the plan
     */
    int getMaxY() {
        return maxY;
    }

    /**
     * Get number of coins on the plan
     * @return Number of coins on the plan
     */
    int getNumberOfCoines() {
        return coins.size();
    }

    /**
     * Calculate the distance between two positions
     * @param from Position to count distance from
     * @param to Position to count distance to
     * @return Distance between the two positions
     */
    static int getDistance(Field from, Field to) {
        int distance = 0;
        distance += Math.abs(from.getX() - to.getX());
        distance += Math.abs(from.getY() - to.getY());
        return distance;
    }
}
