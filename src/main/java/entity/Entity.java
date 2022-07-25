package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    // X,Y Coordinates for Entity Position
    protected int worldX, worldY;

    // Movement Speed for Entities that move.
    protected int speed;

    // Images to handle Entity facing each direction when not moving.
    protected BufferedImage up;
    protected BufferedImage down;
    protected BufferedImage right;
    protected BufferedImage left;

    // Direction Entity is facing.
    protected Direction direction;

    // Collision Detection Details
    protected Rectangle collisionBounds;
    protected boolean collisionHappening = false;
    public final static int CollisionCheckDistance = 4;

    // Sprite management Variables
    protected int spriteCounter = 0;       // Counts up to Duration every frame.
    protected int spriteNum = 0;           // Stores what sprite image should be displayed as an index
    protected int spriteMax = 2;           // Quantity of movement sprites in each array (Currently must be the same)
    protected int spriteDuration = 10;     // millisecond delay between new spites

    // Arrays to store images for movement in each direction.
    // These will be populated and iterated through as necessary when applicable.
    protected BufferedImage[] moveUp;
    protected BufferedImage[] moveDown;
    protected BufferedImage[] moveRight;
    protected BufferedImage[] moveLeft;

    public Entity(int worldX, int worldY, int speed) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        direction = Direction.DOWN;
    }

    /**
     * Get entity X Position
     * @return integer representing distance from the right of the screen.
     */
    public int getWorldX() {
        return worldX;
    }

    /**
     * Get entity Y Position
     * @return integer representing distance from the top of the screen.
     */
    public int getWorldY() {
        return worldY;
    }

    /**
     * Get entity speed
     * @return integer representing speed of the entity.
     */
    public int getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getCollisionBoundsX() {
        return (int) Math.round(collisionBounds.getX());
    }

    public int getCollisionBoundsY() {
        return (int) Math.round(collisionBounds.getY());
    }

    public int getCollisionBoundsHeight() {
        return (int) Math.round(collisionBounds.getHeight());
    }

    public int getCollisionBoundsWidth() {
        return (int) Math.round(collisionBounds.getWidth());
    }

    public boolean isCollisionHappening() {
        return collisionHappening;
    }

    public void setCollisionHappening(boolean collisionHappening) {
        this.collisionHappening = collisionHappening;
    }
}
