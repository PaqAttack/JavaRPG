package paquin.tiles;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean walkable;

    public Tile(boolean walkable, BufferedImage img) {
        this.image = img;
        this.walkable = walkable;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
