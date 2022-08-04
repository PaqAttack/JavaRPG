package tiles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile {
    private int tilesetID;

    private int height;
    private int width;
    private BufferedImage img;

    private boolean isWalkable;

    // Animation properties.
    private boolean hasAnimation = false;
    private AnimationFrame[] animationFrame;

    // These need to be transient to avoid errors with GSON imports.
    // Not 100% sure why since the names dont match imported properties... Something to look up.
    private transient ArrayList<BufferedImage> animationImages = new ArrayList<>();
    private transient int currentIndex = 0;
    private transient long lastUpdate = 0;
    private transient int worldX, worldY;

    public Tile(BufferedImage img) {
        this.img = img;
    }

    public Tile(BufferedImage img, boolean hasAnimation, AnimationFrame[] animationFrame, ArrayList<BufferedImage> animationImages, int currentIndex, long lastUpdate, int worldX, int worldY) {
        this.img = img;
        this.hasAnimation = hasAnimation;
        this.animationFrame = animationFrame;
        this.animationImages = animationImages;
        this.currentIndex = currentIndex;
        this.lastUpdate = lastUpdate;
        this.worldX = worldX;
        this.worldY = worldY;
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

    public boolean hasAnimation() {
        return hasAnimation;
    }

    public AnimationFrame[] getAnimationSequence() {
        return animationFrame;
    }

    public void setHasAnimation(boolean hasAnimation) {
        this.hasAnimation = hasAnimation;
    }

    public void setAnimationSequence(AnimationFrame[] animationFrame) {
        this.animationFrame = animationFrame;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public ArrayList<BufferedImage> getAnimationImages() {
        return animationImages;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void goToNextIndex() {
        if (currentIndex + 1 == animationImages.size()) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
    }

    public int getTimeToNextFrame() {
        return animationFrame[currentIndex].getDuration();
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
