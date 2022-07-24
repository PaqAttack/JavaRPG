package map;

public class Layer {
    private int id;
    private String name;
    private int height, width;
    private int x = 0, y = 0;
    private boolean visible;
    private int[] data;
    private Obstacle[] objects;

    public Layer(int id, String name, int height, int width) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
    }
}
