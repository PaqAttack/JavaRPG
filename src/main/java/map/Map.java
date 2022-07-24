package map;

public class Map {
    private int height, width;
    private int tileheight;
    private int tilewidth;
    private Layer[] layers;

    public Map(int height, int width) {
        this.height = height;
        this.width = width;
    }
}
