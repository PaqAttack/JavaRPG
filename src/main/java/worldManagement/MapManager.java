package worldManagement;

import core.ScreenVar;
import entity.Player;
import map.Layer;
import map.Map;
import tiles.Tile;
import tiles.TileSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Handles all map/tile loading and map display.
 */
public class MapManager {

    private TileSetLoader tilesetLoader;
    private MapLoader mapLoader;
    private final Player player;

    private BufferedImage singleImage;

    private final String jsonTilesheetPath = "src/main/resources/JSON tilesheets/";
    private final String jsonRSSTilesheetPath = "/JSON tilesheets/";
    private final String jsonMapPath = "/JSON maps/";

    // Loaded data
    private ArrayList<TileSet> tileSets;
    private Map currentMap;
    private long timeInMillis;

    private int tileID = 0;
    private int worldX = 0;
    private int worldY = 0;
    private int screenX = 0;
    private int screenY = 0;
    private int tWorldX = 0;
    private int tWorldY = 0;
    private int tScreenX = 0;
    private int tScreenY = 0;
    private int tileSize = ScreenVar.TILE_SIZE.getValue();

    public MapManager(String startingMap, Player player) {
        // Get tileset loader ready
        tilesetLoader = new TileSetLoader();
        this.player = player;

        // Load every tileset in resources.
        loadAvailableTileSets();

        // Save all tile sets for easy access.
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

        // Create Single map
        concatImage(currentMap);

        // Delete mapLoader when no longer necessary
        mapLoader = null;
    }

    /**
     * Draw all static elements of the background onto a single image for background rendering
     * @param map Map to use as a source for the new image.
     */
    private void concatImage(Map map) {
        BufferedImage concatImage = new BufferedImage(map.getWidth() * ScreenVar.TILE_SIZE.getValue(), map.getHeight() * ScreenVar.TILE_SIZE.getValue(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();

        // Cycle through all layers
        for (Layer layer : map.getLayers()) {

            // If there is layer data then process it
            if (layer.getData() != null) {

                // Cycle through just as if we were rendering images
                for (int x = layer.getX(); x < layer.getWidth(); x++) {
                    for (int y = layer.getY(); y < layer.getHeight(); y++) {
                        tileID = layer.getMapData()[y][x];

                        // If this layer at this location isn't non-existent then proceed.
                        if (map.getMapTileSet().get(tileID) != null) {

                            // If a tile is not animated draw it onto the new image.
                            if (!map.getMapTileSet().get(tileID).hasAnimation()) {
                                g2d.drawImage(map.getMapTileSet().get(tileID).getImage(), x * ScreenVar.TILE_SIZE.getValue(), y * ScreenVar.TILE_SIZE.getValue(), null);
                            } else {

                                // it has an animation so add it to the list of animated tiles.
                                map.getAnimatedTiles().add(new Tile(map.getMapTileSet().get(tileID).getImage(),
                                        true,
                                        map.getMapTileSet().get(tileID).getAnimationSequence(),
                                        map.getMapTileSet().get(tileID).getAnimationImages(),
                                        0,
                                        System.currentTimeMillis(),
                                        x,
                                        y));

                            }
                        }

                    }
                }
            }
        }

        g2d.dispose();

        singleImage = concatImage;

        /*
        DEBUG Function to see new image

        try {
            ImageIO.write(concatImage, "png", new File("C:\\Users\\Christopher Paquin\\desktop\\newImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        */
    }

    /**
     * Gets a list of all tile sets in the jsonTilesheetPath resource folder and loads it.
     */
    private void loadAvailableTileSets() {
        File folder = new File(jsonTilesheetPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                tilesetLoader.loadTileSet(jsonRSSTilesheetPath + listOfFiles[i].getName());
            }
        }
    }

    /**
     * Find a tile set by its file name minus extension.
     * This connects JSON and png files.
     * @param name String of a tile set to locate
     * @return tile set with a matching name.
     */
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
        // rendering every tile at 24/16 = 4.7m to 5.6m
        // rendering time single image at 24/16 = 870k to 1.02m

//        long bgStart = System.nanoTime();

        screenX = worldX - player.getWorldX() + player.getScreenX();
        screenY = worldY - player.getWorldY() + player.getScreenY();

        g2.drawImage(singleImage, screenX, screenY, singleImage.getWidth(), singleImage.getHeight(), null);

//        long bg_passed = System.nanoTime() - bgStart;

//        long aniStart = System.nanoTime();

        for (Tile animatedTile : currentMap.getAnimatedTiles()) {

            tWorldX = animatedTile.getWorldX() * tileSize;
            tWorldY = animatedTile.getWorldY() * tileSize;
            tScreenX = tWorldX - player.getWorldX() + player.getScreenX();
            tScreenY = tWorldY - player.getWorldY() + player.getScreenY();

            if (tWorldX + tileSize > player.getWorldX() - player.getScreenX() &&
                    tWorldX - tileSize < player.getWorldX() + player.getScreenX() &&
                    tWorldY + tileSize > player.getWorldY() - player.getScreenY() &&
                    tWorldY - tileSize < player.getWorldY() + player.getScreenY()) {

                g2.drawImage(animatedTile.getAnimationImages().get(animatedTile.getCurrentIndex()), tScreenX, tScreenY, tileSize, tileSize, null);
            }

            timeInMillis = System.currentTimeMillis();

            if (timeInMillis >= animatedTile.getLastUpdate() + animatedTile.getTimeToNextFrame()) {
                animatedTile.goToNextIndex();
                animatedTile.setLastUpdate(timeInMillis);
            }

        }

        // DEBUG
//        long ani_passed = System.nanoTime() - aniStart;
//        g2.setColor(Color.WHITE);
//        g2.drawString("Background time: " + bg_passed, 10, 50);
//        g2.drawString("Animated Tile time: " + ani_passed, 10, 70);
//
//        System.out.println("Background time: " + bg_passed);
//        System.out.println("Animated Tile time: " + ani_passed);

    }

}
