package worldManagement;

import core.ScreenVar;
import entity.Player;
import map.Layer;
import map.Map;
import tiles.TileSet;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MapManager {

    private TileSetLoader tilesetLoader;
    private MapLoader mapLoader;
    private Player player;

    private final String jsonTilesheetPath = "src/main/resources/JSON tilesheets/";
    private final String jsonRSSTilesheetPath = "/JSON tilesheets/";
    private final String jsonMapPath = "/JSON maps/";

    // Loaded data
    private ArrayList<TileSet> tileSets;
    private Map currentMap;

    private int tileID = 0;
    private int worldX = 0;
    private int worldY = 0;
    private int screenX = 0;
    private int screenY = 0;
    private int tileSize = ScreenVar.TILE_SIZE.getValue();

    public MapManager(String startingMap, Player player) {
        // Get tileset loader ready
        tilesetLoader = new TileSetLoader();
        this.player = player;

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

        // Process Layers
        currentMap.processLayers(this);

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

    public TileSet getTileSetByName(String name) {
        for (TileSet ts : tileSets) {
            String tileSetName = ts.getImageFileName();
            String[] cutName = tileSetName.split("\\.");
            if (cutName[0].equalsIgnoreCase(name)) {
                return ts;
            }
        }
        System.out.println("Tile set named (" + name + ") not found.");
        return null;
    }

    public int[][] getCollisionMap() {
        return currentMap.getCollisionMap();
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void render(Graphics2D g2) {
        // DEBUG
        long start = System.nanoTime();

        for (Layer layer : currentMap.getLayers()) {

            if (layer.getData() != null) {

                for (int x = layer.getX(); x < layer.getWidth(); x++) {
                    for (int y = layer.getY(); y < layer.getHeight(); y++) {
                        tileID = layer.getMapData()[y][x];

                        worldX = x * tileSize;
                        worldY = y * tileSize;
                        screenX = worldX - player.getWorldX() + player.getScreenX();
                        screenY = worldY - player.getWorldY() + player.getScreenY();

                        // If tile is visible on screen then render it.
                        if (worldX + tileSize > player.getWorldX() - player.getScreenX() &&
                                worldX - tileSize < player.getWorldX() + player.getScreenX() &&
                                worldY + tileSize > player.getWorldY() - player.getScreenY() &&
                                worldY - tileSize < player.getWorldY() + player.getScreenY() &&
                                tileID != 0){

                            g2.drawImage(currentMap.getMapTileSet().get(tileID).getImage(), screenX, screenY, tileSize, tileSize, null);
                        }
                    }
                }

            }
        }

        // DEBUG
        long end = System.nanoTime();
        long passed = end - start;
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(passed), 10, 50);
        System.out.println(passed);

    }

}
