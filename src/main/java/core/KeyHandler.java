package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for the game.
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean playerMoving;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = true;
            playerMoving = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
            playerMoving = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
            playerMoving = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
            playerMoving = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
            playerMoving = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
            playerMoving = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
            playerMoving = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
            playerMoving = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { /* Method not used */ }
}
