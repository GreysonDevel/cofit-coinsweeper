package eu.greyson.coinsweeper.game;


import java.util.List;
import java.util.Objects;

final class Warden {

    private final static int WARDEN_SPEED = 2;

    private final List<Field> wardenPosititons;
    private int wardenPositionIndex = 0;

    Warden(List<Field> wardenPosititons) {

        Objects.requireNonNull(wardenPosititons);
        this.wardenPosititons = wardenPosititons;
    }

    void move() {

        wardenPositionIndex += WARDEN_SPEED;

        if (wardenPositionIndex >= wardenPosititons.size()) {
            wardenPositionIndex -= wardenPosititons.size();
        }
    }

    Field getWardenPosition() {
        return wardenPosititons.get(wardenPositionIndex);
    }
}
