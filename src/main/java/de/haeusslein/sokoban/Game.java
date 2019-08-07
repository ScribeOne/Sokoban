package de.haeusslein.sokoban;

import java.util.*;

public class Game {

    private List<SokobanLevel> levels;

    public Game(List<SokobanLevel> levels) {
        this.levels = levels;
        startGame();
    }

    /* some rnd comment */
    private void startGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome! \n Please enter your name..");
        String name = scanner.next();

        System.out.printf("Hello %s.\n", name);
        System.out.printf("There are %d Levels:\n", levels.size());
        for (SokobanLevel sokobanLevel : levels) {
            System.out.printf("(%d) %s (%s) \n",
                    levels.indexOf(sokobanLevel) + 1,
                    sokobanLevel.getLevelName(),
                    sokobanLevel.getDifficulty().toString()
            );
        }
        System.out.println("Please choose a level:");
        int selectedLevel = scanner.nextInt();


        boolean keepPlaying = true;
        Sokoban sokoban = new Sokoban();
        char[][] level = levels.get(selectedLevel - 1).getLevelData();
        String[] array = {"N", "E", "S", "W", "X"};
        Set<String> commands = new HashSet<String>(Arrays.asList(array));


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
}
