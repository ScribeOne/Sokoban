package de.haeusslein.sokoban.util;

import de.haeusslein.sokoban.SokobanLevel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;

public class SokobanUtils {


    public List<SokobanLevel> loadLevels(String dirPath) {
        List<SokobanLevel> list = new LinkedList<>();

        try {
            Path dir = Paths.get(getClass().getResource(dirPath).toURI());
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.xml");
            for (Path path : stream) {
                SokobanLevel level = new SokobanLevel(path.getFileName().toString());
                list.add(level);
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();

        }

        return list;
    }
}
