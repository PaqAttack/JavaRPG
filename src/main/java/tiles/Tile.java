package tiles;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;

    public Tile(BufferedImage img) {
        this.image = img;
    }

    public BufferedImage getImage() {
        return image;
    }
}
