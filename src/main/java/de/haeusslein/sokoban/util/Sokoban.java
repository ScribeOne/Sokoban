package de.haeusslein.sokoban.util;

import de.haeusslein.sokoban.util.Pair;

public class Sokoban {

    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }


    public boolean moveNorth(char[][] level) {
        return move(level, Direction.NORTH);
    }

    public boolean moveEast(char[][] level) {
        return move(level, Direction.EAST);
    }

    public boolean moveSouth(char[][] level) {
        return move(level, Direction.SOUTH);
    }

    public boolean moveWest(char[][] level) {
        return move(level, Direction.WEST);
    }


    private boolean move(char[][] level, Direction direction) {

        // find the player
        Pair<Integer, Integer> playerPosition = findPlayer(level);

        // Initialize Variables
        int playerX = playerPosition.getFirst();
        int playerY = playerPosition.getSecond();
        int movementX = 0;
        int movementY = 0;


        if (playerX == -1 || playerY == -1) {
            return false;
        }

        switch (direction) {
            case NORTH:
                movementX = -1;
                break;
            case EAST:
                movementY = 1;
                break;
            case SOUTH:
                movementX = 1;
                break;
            case WEST:
                movementY = -1;
                break;
        }

        int playerXNext = playerX + movementX;
        int playerYNext = playerY + movementY;

        int playerXNext2 = playerX + 2 * movementX;
        int playerYNext2 = playerY + 2 * movementY;

        // Target is empty
        if (level[playerXNext][playerYNext] == '.') {
            level[playerXNext][playerYNext] = '@';
            level[playerX][playerY] = '.';
            return true;
        }

        // Target is a box
        if (level[playerXNext][playerYNext] == '$') {


            // Field behind Target is empty
            if (level[playerXNext2][playerYNext2] == '.') {

                // move player and box
                level[playerX][playerY] = '.';
                level[playerXNext][playerYNext] = '@';
                level[playerXNext2][playerYNext2] = '$';
                return true;
            }
        }
        return false;
    }


    /**
     * Find a player in a sokoban level
     *
     * @param sokoban level to search in
     * @return Postition of the Player, (-1,-1) when not found
     */
    public Pair<Integer, Integer> findPlayer(char[][] sokoban) {
        for (int i = 0; i < sokoban.length; i++) {
            for (int j = 0; j < sokoban.length; j++) {
                if (sokoban[i][j] == '@') {
                    return new Pair<>(i, j);
                }
            }
        }
        return new Pair<>(-1, -1);
    }

    public void printSokoban(char[][] level) {
        System.out.println(sokobanToString(level));
    }


    public String sokobanToString(char[][] level) {
        String levelAsString = "";
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level.length; j++) {
                levelAsString = levelAsString + level[i][j];
            }
            levelAsString = levelAsString + "\n";
        }
        return levelAsString;
    }
}