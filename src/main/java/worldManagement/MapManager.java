package worldManagement;

import map.Map;
import tiles.TileSet;

import java.io.File;
import java.util.ArrayList;

public class MapManager {

    private TileSetLoader tilesetLoader;
    private MapLoader mapLoader;

    private final String jsonTilesheetPath = "src/main/resources/JSON tilesheets/";
    private final String jsonRSSTilesheetPath = "/JSON tilesheets/";
    private final String jsonMapPath = "/JSON maps/";

    // Loaded data
    private ArrayList<TileSet> tileSets;
    private Map currentMap;

    public MapManager(String startingMap) {
        // Get tileset loader ready
        tilesetLoader = new TileSetLoader();

        // Load every tileset in resources.
        loadAvailableTileSets();

        // Save all tilesets for easy access.
        tileSets = tilesetLoader.getTileSets();

        // Delete tilesetLoader when no longer necessary
        tilesetLoader = null;

        // HANDLE MAP
        // Load up starting map
        mapLoader = new MapLoader(jsonMapPath + startingMap);

        // Save current map for easy access.
        currentMap = mapLoader.getMap();

        // Delete mapLoader when no longer necessary
        mapLoader = null;

        // Do garbage collection.
        System.gc();
    }

    private void loadAvailableTileSets() {
        File folder = new File(jsonTilesheetPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                tilesetLoader.loadTileSet(jsonRSSTilesheetPath + listOfFiles[i].getName());
            }
        }
    }
}
