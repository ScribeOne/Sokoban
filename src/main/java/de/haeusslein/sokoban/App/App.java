package de.haeusslein.sokoban.App;

import de.haeusslein.sokoban.Game;
import de.haeusslein.sokoban.SokobanLevel;
import de.haeusslein.sokoban.util.SokobanUtils;

import java.util.List;


/**
 * PvS Project Summer semester 2019
 *
 * @author scribe
 */
public class App {
    public static void main(String[] args) {
        SokobanUtils sokobanUtils = new SokobanUtils();
        List<SokobanLevel> levels = sokobanUtils.loadLevels("/");
        Game sokobanGame = new Game(levels);
    }
}
