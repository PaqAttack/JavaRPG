package map;

import tiles.Tile;
import worldManagement.MapManager;

import java.util.*;

public class Map {
    private int height, width;
    private Layer[] layers;
    private MapTileSet[] tilesets;
    private transient ArrayList<Tile> mapTileSet;
    private transient int[][] collisionMap;

    /**
     * This is intended to be created from JSON files only using exports from the Tiled map editor.
     * @param height
     * @param width
     */
    public Map(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void processLayers(MapManager mapMngr) {
        for (Layer layer : layers) {
            layer.processDataArray();
        }

        // initialize mapTileSet. This is a solitary list of all tiles from all tilesets for THIS map.
        mapTileSet = new ArrayList<>();

        // Index 0 is never used.
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

    private void createCollisionMap() {
        collisionMap = new int[height][width];

        for (Layer layer : layers) {

            for (int x = layer.getX(); x < layer.getWidth(); x++) {
                for (int y = layer.getY(); y < layer.getHeight(); y++) {
                    int tileID = layer.getMapData()[y][x];
                    if (tileID != 0 && !mapTileSet.get(tileID).isWalkable()) {
                        collisionMap[y][x] = 1;
                    }
                }
            }
        }

    }

    public Layer[] getLayers() {
        return layers;
    }

    public ArrayList<Tile> getMapTileSet() {
        return mapTileSet;
    }

    public int[][] getCollisionMap() {
        return collisionMap;
    }
}
