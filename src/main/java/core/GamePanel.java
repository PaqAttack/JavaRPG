package core;

import entity.Player;
import world.MapManager;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GamePanel extends JPanel implements Runnable {

    private final KeyHandler keyHandler = new KeyHandler();
    private MapManager mapManager;
    private Player player;
    private CollisionChecker collisionChecker;

    private int origTileSize;
    private int scale;
    private int tileSize;
    private int screenCols;
    private int screenRows;
    private int screenWidth;
    private int screenHeight;

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    // Create and setup game panel object
    public GamePanel() {
        LogTest.setupLogger();

        // Load game Properties
        loadGameProperties();

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setFocusable(true);

        // De-clutters this method. Creates the player, map manager and collision checker.
        createInitialGameObjects();
    }

    private void loadGameProperties() {
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream("src/Properties.properties")){
            properties.load(fileInputStream);
            logger.log(Level.INFO, "Properties file successfully loaded.");
        } catch (IOException e) {
            logger.log(Level.INFO, "Properties file failed to load.");
            logger.log(Level.SEVERE, e.toString());
            e.printStackTrace();
        }

        origTileSize = Integer.parseInt(properties.getProperty("DefaultTileSize"));
        scale = Integer.parseInt(properties.getProperty("Scale"));
        tileSize = origTileSize * scale;
        screenCols = Integer.parseInt(properties.getProperty("ScreenColumns"));
        screenRows = Integer.parseInt(properties.getProperty("ScreenRows"));
        screenWidth = screenCols * tileSize;
        screenHeight = screenRows * tileSize;
    }

    /**
     * Creates initial game objects.
     */
    private void createInitialGameObjects() {
        // Create the player
        int startTileRow = tileSize * 5;
        int startTileCol = tileSize * 5;
        player = new Player(this, keyHandler, startTileCol, startTileRow, 250);

        // Creates the Tile Manager
        // The map that will load on game start.
        String startingMapFileName = "FlowingWaterMap.json";
        mapManager = new MapManager(this, startingMapFileName, player);

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
     *
     * @param g Graphics Object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.render(g);
    }

    /**
     * Update method handles updating all game objects.
     *
     * @param deltaTime The time since last update. This prevents faster computers from having a different game experience from a slow computer.
     */
    private void update(double deltaTime) {
        player.update(deltaTime);
    }

    /**
     * Draws all objects on screen.
     *
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

    public int getOrigTileSize() {
        return origTileSize;
    }

    public int getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenCols() {
        return screenCols;
    }

    public int getScreenRows() {
        return screenRows;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
