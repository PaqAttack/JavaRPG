package map;

public class Layer {
    private int id;
    private String name;
    private int height, width;
    private int x = 0, y = 0;
    private boolean visible;
    private int[] data;
    private Obstacle[] objects;

    /*
     Implementation of this class has not been done yet. This is a major conversion from a much simpler,
     currently implemented map system. There is currently no connection between this package and the rest of the game.
     */


    /**
     * This will store a single layer within a Map object.
     * Expected layers are Base layer, props layer and Collision layer.
     * @param id ID of the layer in the Map
     * @param name name of the layer.
     * @param height height of the layer in number of tiles
     * @param width width of the layer in number of tiles
     */
    public Layer(int id, String name, int height, int width) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
    }
}
