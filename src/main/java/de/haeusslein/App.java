package de.haeusslein;

import de.haeusslein.util.Sokoban;

/**
 * PvS Project Summer semester 2019
 *
 * @author scribe
 */
public class App {
    public static void main(String[] args) {
        playSokoban();
    }

    private static void playSokoban() {
        char[][] sokoban = new char[7][];
        sokoban[0] = "#######".toCharArray();
        sokoban[1] = "#.....#".toCharArray();
        sokoban[2] = "#..$..#".toCharArray();
        sokoban[3] = "#.$@$.#".toCharArray();
        sokoban[4] = "#..$..#".toCharArray();
        sokoban[5] = "#.....#".toCharArray();
        sokoban[6] = "#######".toCharArray();

        Sokoban sokobanClass = new Sokoban();

        System.out.println(sokobanClass.sokobanToString(sokoban));
        sokobanClass.moveNorth(sokoban);
        System.out.println(sokobanClass.sokobanToString(sokoban));
        sokobanClass.moveEast(sokoban);
        System.out.println(sokobanClass.sokobanToString(sokoban));
        sokobanClass.moveSouth(sokoban);
        System.out.println(sokobanClass.sokobanToString(sokoban));
    }
}
