package map;

import tiles.Tile;
import world.MapManager;

import java.util.*;

public class Map {
    private final int height;
    private final int width;
    private Layer[] layers;
    private MapTileSet[] tilesets;
    private transient ArrayList<Tile> mapTileSet;
    private transient int[][] collisionMap;
    private transient ArrayList<Tile> animatedTiles;


    /**
     * This is intended to be created from JSON files only using exports from the Tiled map editor.
     *
     * @param height Number of tiles high the map is.
     * @param width  Number of tiles wide the map is.
     */
    public Map(int height, int width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Process all layers and prepare the data to be used by the game.
     *
     * @param mapMngr Object that runs all map management.
     */
    public void processLayers(MapManager mapMngr) {
        for (Layer layer : layers) {
            layer.processDataArray();
        }

        // initialize mapTileSet. This is a solitary list of all tiles from all tilesets for THIS map.
        mapTileSet = new ArrayList<>();

        // This holds all tiles with animated data. These tiles still exist as static images in
        // mapTileSet and may be used in a non-animated way.
        animatedTiles = new ArrayList<>();

        // Index 0 is never used. Tile ID 0 means a layer has no data on the given location
        mapTileSet.add(null);

        // sort maptilesets by first global IDs so the resulting mapTileSet indexes will match for all tiles.
        Arrays.sort(tilesets, Comparator.comparingInt(MapTileSet::getFirstgid));

        // find all coresponding Tile sets by name and load them into Tile array.
        for (MapTileSet mts : tilesets) {
            // load tiles into mapTileSet
            if (mapMngr.getTileSetByName(mts.getName()).getTiles() != null) {
                mapTileSet.addAll(mapMngr.getTileSetByName(mts.getName()).getTiles());
            } else {
                System.out.println("Error in processLayers of map.java");
                System.out.println("Looking for map tile set name: " + mts.getName());
                System.out.println("Found: " + mapMngr.getTileSetByName(mts.getName()).getImageFileName());
            }
        }

        // Create array to hold collision data for current map.
        createCollisionMap();
    }

    /**
     * Populates the collision map with collision data from all the layers of the current map.
     * If any tile in any layer of the array is impassable then that tile is blocked.
     */
    private void createCollisionMap() {
        // Initialize collision array
        collisionMap = new int[height][width];

        for (Layer layer : layers) {

            // Scan the grid of the layer
            for (int x = layer.getX(); x < layer.getWidth(); x++) {
                for (int y = layer.getY(); y < layer.getHeight(); y++) {

                    // get the tile ID at specified location
                    int tileID = layer.getMapData()[y][x];

                    // 0 means no data so skip that and if thats not the case then check if given tile ID is walkable.
                    if (tileID != 0 && !mapTileSet.get(tileID).isWalkable()) {

                        // 0 (default) is walkable and 1 is blocked.
                        collisionMap[x][y] = 1;
                    }
                }
            }
        }
    }

    public Layer[] getLayers() {
        return layers;
    }

    public List<Tile> getAnimatedTiles() {
        return animatedTiles;
    }

    public List<Tile> getMapTileSet() {
        return mapTileSet;
    }

    public int[][] getCollisionMap() {
        return collisionMap;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
