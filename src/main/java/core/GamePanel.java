package core;

import entity.Player;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private KeyHandler keyHandler = new KeyHandler();
    private TileManager tileManager;
    private Player player;
    private CollisionChecker collisionChecker;

    // Create and setup game panel object
    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenVar.SCREEN_WIDTH.getValue(), ScreenVar.SCREEN_HEIGHT.getValue()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        createInitialGameObjects();
    }

    private void createInitialGameObjects() {
        // Create the player
        int startTileRow = ScreenVar.TILE_SIZE.getValue() * 5;
        int startTileCol = ScreenVar.TILE_SIZE.getValue() * 5;
        player = new Player(this, keyHandler, startTileCol, startTileRow, 200);

        // Creates the Tile Manager
        tileManager = new TileManager(player);

        // Creates Collision Checker
        collisionChecker = new CollisionChecker(this);
    }

    /**
     * Starts the main game thread that controls the update/render loops
     */
    public void startGameThread() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        boolean running = true;
        final int MAX_FPS = 100;
        final int MAX_UPS = 60;

        // Number of nanoseconds between each update and render
        final double UPDATE_TARGET_TIME = 1000000000.0 / MAX_UPS;
        final double RENDER_TARGET_TIME = 1000000000.0 / MAX_FPS;

        double uDeltaTime = 0;
        double fDeltaTime = 0;

        int frames = 0;
        int updates = 0;

        long startTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        while (running) {
            long currentTime = System.nanoTime();
            uDeltaTime += currentTime - startTime;
            fDeltaTime += currentTime - startTime;
            startTime = currentTime;

            if (uDeltaTime >= UPDATE_TARGET_TIME) {
                update(uDeltaTime / 1000000000);
                updates++;
                uDeltaTime -= UPDATE_TARGET_TIME;
            }

            if (fDeltaTime >= RENDER_TARGET_TIME) {
                repaint();
                frames++;
                fDeltaTime -= RENDER_TARGET_TIME;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("UPS: " + updates + ", FPS: " + frames);
                updates = 0;
                frames = 0;
                timer += 1000;
            }
        }
    }

    /**
     * Redirect the screen rendering controls to the render() method.
     * @param g Graphics Object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.render(g);
    }

    /**
     * Update method handles updating all game objects.
     * @param deltaTime The time since last update. This prevents faster computers from having a different game experience from a slow computer.
     */
    private void update(double deltaTime) {
        player.update(deltaTime);

    }

    /**
     * Draws all objects on screen.
     * @param g Graphics Object
     */
    public void render(Graphics g) {
        // Cast Graphics object to Graphics2D for additional capabilities.
        Graphics2D g2 = (Graphics2D) g;


        try {
            tileManager.render(g2);
            player.render(g2);
        } finally {
            g2.dispose();
        }


    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public TileManager getTileManager() {
        return tileManager;
    }
}
