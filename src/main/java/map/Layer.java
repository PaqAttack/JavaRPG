package map;

public class Layer {
    private int id;
    private final String name;
    private final int height;
    private final int width;
    private int x = 0;
    private int y = 0;
    private boolean visible;
    private int[] data;
    private int[][] mapData;

    /**
     * This will store a single layer within a Map object.
     * Expected layers are Base layer, props layer and Collision layer.
     *
     * @param id     ID of the layer in the Map
     * @param name   name of the layer.
     * @param height height of the layer in number of tiles
     * @param width  width of the layer in number of tiles
     */
    public Layer(int id, String name, int height, int width) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
    }

    /**
     * Transfers all map tile special properties to a mapData array.
     * This array is used in the creation of the collision map.
     * <p>
     * More data will likely be added later.
     */
    public void processDataArray() {
        int myIndex = 0;
        mapData = new int[width][height];

        // data array stores the custom properties for each tile such as "blocked"
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapData[y][x] = data[myIndex];
                myIndex++;
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getData() {
        return data;
    }

    public int[][] getMapData() {
        return mapData;
    }

    public String getName() {
        return name;
    }
}
