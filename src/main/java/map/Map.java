package map;

public class Map {
    private int height, width;
    private Layer[] layers;
    private MapTileSet[] tilesets;

    /*
    Implementation of this class has not been done yet. This is a major conversion from a much simpler,
    currently implemented map system. There is currently no connection between this package and the rest of the game.
    */

    /**
     * This is intended to be created from JSON files only using exports from the Tiled map editor.
     * @param height
     * @param width
     */
    public Map(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void processLayers() {

    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public MapTileSet[] getTilesets() {
        return tilesets;
    }
}
