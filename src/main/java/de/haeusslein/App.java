package de.haeusslein;

import de.haeusslein.util.Sokoban;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * PvS Project Summer semester 2019
 *
 * @author scribe
 */
public class App {
    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        Scanner scanner;
        boolean keepPlaying = true;
        Sokoban sokoban = new Sokoban();
        char[][] level = createSokoban();
        String[] array = {"N", "E", "S", "W", "X"};
        Set<String> commands = new HashSet<String>(Arrays.asList(array));


        scanner = new Scanner(System.in);
        while (keepPlaying) {
            sokoban.printSokoban(level);
            System.out.println("Where do you want to go? (N/E/S/W or X to exit)");
            String input = scanner.next();
            input = input.toUpperCase();

            if (!commands.contains(input)) {
                System.out.println("Command not found");
            } else {
                boolean success = false;
                switch (input) {
                    case "N":
                        success = sokoban.moveNorth(level);
                        break;
                    case "E":
                        success = sokoban.moveEast(level);
                        break;
                    case "S":
                        success = sokoban.moveSouth(level);
                        break;
                    case "W":
                        success = sokoban.moveWest(level);
                        break;
                    case "X":
                        keepPlaying = false;
                        System.out.println("Goodbye!");
                        return;
                }
                if (!success) {
                    System.err.println("Can't move there..");
                }

            }
        }
        scanner.close();
    }

    private static char[][] createSokoban() {
        char[][] sokoban = new char[7][];
        sokoban[0] = "#######".toCharArray();
        sokoban[1] = "#.....#".toCharArray();
        sokoban[2] = "#..$..#".toCharArray();
        sokoban[3] = "#.$@$.#".toCharArray();
        sokoban[4] = "#..$..#".toCharArray();
        sokoban[5] = "#.....#".toCharArray();
        sokoban[6] = "#######".toCharArray();
        return sokoban;
    }
}
