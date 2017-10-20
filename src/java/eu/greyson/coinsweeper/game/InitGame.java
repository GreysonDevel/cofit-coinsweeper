package eu.greyson.coinsweeper.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class InitGame {

    private static final String mapsPath = "src/maps/";
    private static final String emptyFieldChars = "Oo";
    private static final String coinsChars = "Xx";
    private static final String wardenChars = "Ww";

    private InitGame() {}

    /** Load map and return game. */
    public static Game loadGame(String fileName) throws IOException {
        return new Game(readFieldFromFile(new File(mapsPath + fileName)));
    }

    /** Load map and return game. */
    public static Game loadGame(File map) throws IOException {
        return new Game(readFieldFromFile(map));
    }

    private static GardenPlan readFieldFromFile(File map) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(map))) {

            Set<Field> mineList = new HashSet<>();
            int sizeX = 0;
            int sizeY = 0;
            int y = 0;
            String line = br.readLine();

            while (!line.isEmpty()) {

                for (int x = 0; x < line.length(); x++) {

                    char ch = line.charAt(x);
                    if (coinsChars.indexOf(ch) >= 0) {
                        mineList.add(new Field(x + Game.MIN_POSITION, y + Game.MIN_POSITION));
                    }

                    if (emptyFieldChars.indexOf(ch) >= 0 || coinsChars.indexOf(ch) >= 0) {
                        if (sizeX < x) {
                            sizeX = x;
                        }
                        if (sizeY < y) {
                            sizeY = y;
                        }
                    }
                }

                line = br.readLine();
                y++;
            }

            //we have remembered the last valid position (starting from 0), map size is lastValidPosition+1
            sizeX += 1;
            sizeY += 1;

            // WARDEN PATH PROCESSING //////////////////

            List<String> wardenLines = new ArrayList<>();

            line = br.readLine();
            while (line != null) {
                wardenLines.add(line);
                line = br.readLine();
            }

            Warden warden = parseWardenPath(wardenLines, sizeX, sizeY);

            System.out.println("Map loaded: " + sizeX + "x" + sizeY + ", " + mineList.size() + " coins");
            return new GardenPlan(sizeX, sizeY, mineList, warden);
        }
    }

    private static Warden parseWardenPath(List<String> lines, int mapWidth, int mapHeight) {

        int wardenMinX = 99999999;
        int wardenMinY = 99999999;

        boolean endLoop = false;

        for (int y = 0; y < lines.size(); y++) {

            final String line = lines.get(y);

            for (int x = 0; x < line.length(); x++) {

                if (wardenChars.indexOf(line.charAt(x)) >= 0) {

                    wardenMinX = x;
                    wardenMinY = y;
                    endLoop = true;
                    break;
                }
            }

            if (endLoop) {
                break;
            }
        }

        LinkedHashSet<Field> visitedFields = new LinkedHashSet<>();

        Function<Field, Boolean> boundaryChecker = field ->
            !(field.getX() < 0 || field.getX() >= mapWidth ||
              field.getY() < 0 || field.getY() >= mapHeight);

        Field currentField = new Field(wardenMinX, wardenMinY);
        visitedFields.add(currentField);

        while (true) {

            Field candidateField = null;

            Field topField =    new Field(currentField.getX(), currentField.getY()-1);
            Field rightField =  new Field(currentField.getX()+1, currentField.getY());
            Field bottomField = new Field(currentField.getX(), currentField.getY()+1);
            Field leftField =   new Field(currentField.getX()-1, currentField.getY());

            Function<Field, Boolean> checkField = ( field ->
                boundaryChecker.apply(field) &&
                !visitedFields.contains(field) &&
                wardenChars.indexOf(lines.get(field.getY()).charAt(field.getX())) >= 0
            );

            if (checkField.apply(topField)) {
                candidateField = topField;
            } else if (checkField.apply(rightField)) {
                candidateField = rightField;
            } else if (checkField.apply(bottomField)) {
                candidateField = bottomField;
            } else if (checkField.apply(leftField)) {
                candidateField = leftField;
            }

            if (candidateField == null) {
                break;
            }

            visitedFields.add(candidateField);
            currentField = candidateField;
        }

        List<Field> wardenPositions = visitedFields.stream()
            .map(f -> new Field(f.getX()+1, f.getY()+1))
            .collect(Collectors.toList());

        return new Warden(wardenPositions);
    }
}
