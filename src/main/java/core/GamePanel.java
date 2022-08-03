package core;

import entity.Player;
import worldManagement.MapManager;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final KeyHandler keyHandler = new KeyHandler();
    private MapManager mapManager;
    private Player player;
    private CollisionChecker collisionChecker;

    // Create and setup game panel object
    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenVar.SCREEN_WIDTH.getValue(), ScreenVar.SCREEN_HEIGHT.getValue()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        // De-clutters this method. Creates the player, map manager and collision checker.
        createInitialGameObjects();
    }

    /**
     * Creates initial game objects.
     */
    private void createInitialGameObjects() {
        // Create the player
        int startTileRow = ScreenVar.TILE_SIZE.getValue() * 5;
        int startTileCol = ScreenVar.TILE_SIZE.getValue() * 5;
        player = new Player(this, keyHandler, startTileCol, startTileRow, 250);

        // Creates the Tile Manager
        // The map that will load on game start.
        String startingMapFileName = "FlowingWaterMap.json";
        mapManager = new MapManager(startingMapFileName, player);

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

    /**
     * This is the main game loop that re-draws the screen and updates the game objects a fixed quantity of times per second.
     */
    @Override
    public void run() {

        // Not implemented yet but this will facilitate pausing and resuming
        boolean running = true;

        // These are the games target frames per second and updates per second.
        final int MAX_FPS = 60;
        final int MAX_UPS = 60;

        // Number of nanoseconds between each update and render
        final double UPDATE_TARGET_TIME = 1000000000.0 / MAX_UPS;
        final double RENDER_TARGET_TIME = 1000000000.0 / MAX_FPS;

        // Records time per loop
        double uDeltaTime = 0;
        double fDeltaTime = 0;

        // Counters for debug /display
        int frames = 0;
        int updates = 0;

        // Loop time measure
        long startTime = System.nanoTime();

        //Used only to track output display
        long timer = System.currentTimeMillis();

        while (running) {
            // Update the timer
            long currentTime = System.nanoTime();

            // adds the duration of the last loop to each variable
            uDeltaTime += currentTime - startTime;
            fDeltaTime += currentTime - startTime;

            // resets start time
            startTime = currentTime;

            // if time that has passed equals or exceeds the target time then update.
            if (uDeltaTime >= UPDATE_TARGET_TIME) {
                update(uDeltaTime / 1000000000);
                updates++;
                uDeltaTime -= UPDATE_TARGET_TIME;
            }

            // if time that has passed equals or exceeds the target time then repaint and render all objects.
            if (fDeltaTime >= RENDER_TARGET_TIME) {
                repaint();
                frames++;
                fDeltaTime -= RENDER_TARGET_TIME;
            }

            // every second this will trigger an output of frames and updates that have occurred in the last second.
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
            mapManager.render(g2);
            player.render(g2);
        } finally {
            g2.dispose();
        }
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

}
