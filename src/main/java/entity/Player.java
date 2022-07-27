package entity;

import core.GamePanel;
import core.KeyHandler;
import core.ScreenVar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Class to handler the main player character for the game.
 * This class will extend the Entity Base class like all other game entities.
 */
public class Player extends Entity {
    // Toying with leaving this here but likely to move it as all keyboard input is player input.
    private KeyHandler keyHandler;
    private GamePanel gamePanel;

    // These will determine where the player is displayed.
    public final int screenX;
    public final int screenY;

    /**
     * Creates a player object.
     *
     * @param keyHandler Key handler.
     * @param startX     Starting Position X value
     * @param startY     Starting Position Y value
     * @param speed      speed of the player.
     */
    public Player(GamePanel gamePanel, KeyHandler keyHandler, int startX, int startY, int speed) {
        super(startX, startY, speed);
        this.keyHandler = keyHandler;
        this.gamePanel = gamePanel;

        screenX = (ScreenVar.SCREEN_WIDTH.getValue() / 2) - (ScreenVar.TILE_SIZE.getValue()/ 2);
        screenY = (ScreenVar.SCREEN_HEIGHT.getValue() / 2) - (ScreenVar.TILE_SIZE.getValue() / 2);

        spriteMax = 4;
        getPlayerImage();

        // Collision detection area of the player area.
        int boundsX = 3 * ScreenVar.SCALE.getValue();
        int boundsY = 10 * ScreenVar.SCALE.getValue();
        int boundsW = ScreenVar.TILE_SIZE.getValue() - (3 * ScreenVar.SCALE.getValue() * 2);
        int boundsH = ScreenVar.TILE_SIZE.getValue() - (10 * ScreenVar.SCALE.getValue());

        collisionBounds = new Rectangle(boundsX, boundsY, boundsW, boundsH);
    }

    /**
     * Loads images for the player to use.
     */
    public void getPlayerImage() {
        moveRight = new BufferedImage[spriteMax];
        moveUp = new BufferedImage[spriteMax];
        moveDown = new BufferedImage[spriteMax];
        moveLeft = new BufferedImage[spriteMax];

        try {
            up = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/faceup.png")));
            moveUp[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkup1.png")));
            moveUp[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkup2.png")));
            moveUp[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkup3.png")));
            moveUp[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkup4.png")));

            down = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/facedown.png")));
            moveDown[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkdown1.png")));
            moveDown[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkdown2.png")));
            moveDown[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkdown3.png")));
            moveDown[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkdown4.png")));

            right = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/faceright.png")));
            moveRight[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkright1.png")));
            moveRight[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkright2.png")));
            moveRight[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkright3.png")));
            moveRight[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkright4.png")));

            left = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/faceleft.png")));
            moveLeft[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkleft1.png")));
            moveLeft[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkleft2.png")));
            moveLeft[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkleft3.png")));
            moveLeft[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walkleft4.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will be called every update.
     *
     * @param deltaTime Seconds since the last frame. Makes the game frame rate independent.
     */
    public void update(double deltaTime) {
        // Player Movement Updates. Ignore if not moving.
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.rightPressed || keyHandler.leftPressed) {
            if (keyHandler.upPressed) {
                direction = Direction.UP;
            }
            if (keyHandler.downPressed) {
                direction = Direction.DOWN;
            }
            if (keyHandler.rightPressed) {
                direction = Direction.RIGHT;
            }
            if (keyHandler.leftPressed) {
                direction = Direction.LEFT;
            }

            // Collision Checking
            collisionHappening = false;
            //gamePanel.getCollisionChecker().checkTile(this);

            // If no collision is happening then move. Otherwise, nope!
            if (!collisionHappening) {
                switch (direction) {
                    case UP -> worldY -= getSpeed() * deltaTime;
                    case DOWN -> worldY += getSpeed() * deltaTime;
                    case LEFT -> worldX -= getSpeed() * deltaTime;
                    case RIGHT -> worldX += getSpeed() * deltaTime;
                }
            }

            updateSpriteCounter();
        }
    }

    private void updateSpriteCounter() {
        // Update sprite counter ever update
        spriteCounter++;
        if (spriteCounter > spriteDuration) {       // If counter has reached the duration then trigger a swap and reset
            spriteCounter = 0;                      // Reset counter.
            if (spriteNum + 1 >= spriteMax) {       // If index is about to exceed max sprites (and array index) then reset to 0
                spriteNum = 0;
            } else {
                spriteNum++;                        // If index is not at the end then move to next index.
            }
        }
    }

    /**
     * Renders the player on the screen.
     *
     * @param g2 Graphics Object used to repaint the screen.
     */
    public void render(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case DOWN -> {
                if (!keyHandler.playerMoving) {
                    image = down;
                } else {
                    image = moveDown[spriteNum];
                }
            }
            case UP -> {
                if (!keyHandler.playerMoving) {
                    image = up;
                } else {
                    image = moveUp[spriteNum];
                }
            }
            case LEFT -> {
                if (!keyHandler.playerMoving) {
                    image = left;
                } else {
                    image = moveLeft[spriteNum];
                }
            }
            case RIGHT -> {
                if (!keyHandler.playerMoving) {
                    image = right;
                } else {
                    image = moveRight[spriteNum];
                }
            }
        }

        // Draw the appropriate image on screen.
        g2.drawImage(image, screenX, screenY, ScreenVar.TILE_SIZE.getValue(), ScreenVar.TILE_SIZE.getValue(), null);

        // DEBUG FEATURES - SHOW COLLISION DETECTION AREA OF PLAYER
        //g2.setColor(Color.GREEN);
        //g2.fillRect(collisionBounds.x, collisionBounds.y, (int) collisionBounds.getWidth(), (int) collisionBounds.getHeight());
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
