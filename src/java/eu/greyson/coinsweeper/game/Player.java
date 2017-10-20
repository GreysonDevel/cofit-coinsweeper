package eu.greyson.coinsweeper.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Player extends Thread {

    private final Map<Field, FieldState> fieldStates = new HashMap<>();
    protected final Game game;

    protected Player(Game game) {
        this.game = game;
        try {
            for (int x = game.getMinX(); x <= game.getMaxX(); x++) {
                for (int y = game.getMinY(); y <= game.getMaxY(); y++) {
                    markFieldAs(new Field(x, y), FieldState.UNKNOWN_FIELD);
                }
            }
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public abstract void findCoins() throws TimeoutException;

    @Override
    public void run() {
        try {
            findCoins();
        }
        catch (Exception e) {
            stop();
        }
    }

    protected final void markFieldAs(Field field, FieldState state) {
        fieldStates.put(field, state);
    }

    protected final boolean isFieldCoin(Field field) {
        return fieldStates.getOrDefault(field, FieldState.UNKNOWN_FIELD).equals(FieldState.COIN_FIELD);
    }

    protected final boolean isFieldEmpty(Field field) {
        return fieldStates.getOrDefault(field, FieldState.UNKNOWN_FIELD).equals(FieldState.EMPTY_FIELD);
    }

    protected final boolean isFieldUnknown(Field field) {
        return fieldStates.getOrDefault(field, FieldState.UNKNOWN_FIELD).equals(FieldState.UNKNOWN_FIELD);
    }

    protected final FieldState getFieldState(Field field) {
        return fieldStates.getOrDefault(field, FieldState.UNKNOWN_FIELD);
    }

    protected final Set<Field> getCoinFields() {
        Set<Field> result = new HashSet<>();
        for (Map.Entry<Field, FieldState> entry : fieldStates.entrySet()) {
            if (entry.getValue().equals(FieldState.COIN_FIELD))
            {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    protected final Set<Field> getEmptyFields() {
        Set<Field> result = new HashSet<>();
        for (Map.Entry<Field, FieldState> entry : fieldStates.entrySet()) {
            if (entry.getValue().equals(FieldState.EMPTY_FIELD))
            {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    protected final Set<Field> getUnknownFields() {
        Set<Field> result = new HashSet<>();
        for (Map.Entry<Field, FieldState> entry : fieldStates.entrySet()) {
            if (entry.getValue().equals(FieldState.UNKNOWN_FIELD))
            {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    protected final void print(String string) {
        System.out.print(string);
    }

    protected final void println(String string) {
        System.out.println(string);
    }
}
