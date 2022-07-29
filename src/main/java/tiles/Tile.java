package tiles;

import java.awt.image.BufferedImage;

public class Tile {
    private int tilesetID;

    private int height, width;
    private BufferedImage img;

    private boolean isWalkable;

    // Animation properties.
    private boolean hasAnimation = false;
    private AnimationSequence[] animationSequence;

    public Tile(BufferedImage img) {
        this.img = img;
    }

    public BufferedImage getImage() {
        return img;
    }

    public void setTilesetID(int tilesetID) {
        this.tilesetID = tilesetID;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setHasAnimation(boolean hasAnimation) {
        this.hasAnimation = hasAnimation;
    }

    public void setAnimationSequence(AnimationSequence[] animationSequence) {
        this.animationSequence = animationSequence;
    }
}
