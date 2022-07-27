package tiles;

import core.ScreenVar;
import core.WorldVar;
import entity.Player;
import map.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    // This array will hold all tiles.
    private ArrayList<Tile> tiles;

    // This will store the currently loaded map.
    private final Map map;

    private ArrayList<Rectangle> obstacles = new ArrayList<>();

    // Hold a reference to the player
    private final Player player;

    private int tileID = 0;
    private int worldX = 0;
    private int worldY = 0;
    private int screenX = 0;
    private int screenY = 0;
    private int tileSize;

    // Temp Location for map file names
    String myFile = "/JSON maps/TestMapwObstacles.json";
    BufferedImage curScreen;


    /**
     * Constructs a Tile Manager.
     */
    public TileManager(Player player){
        this.player = player;

        // Initialize arrays.
        tiles = new ArrayList<>();

        // Load JSON File
        MapLoader maploader = new MapLoader(myFile);
        map = maploader.getMap();

        tileSize = map.getTilewidth() * ScreenVar.SCALE.getValue();

        // GSON imports my data as a 1D array and it would be much better if it were 2D so lets fix that.
        for (Layer layer : map.getLayers()) {
            layer.processDataArray();
        }

        // Build collision Array
        buildCollisionAreas();

        // Cut Up Sprite Sheet and create a lot of Tiles.
        loadSpriteSheets();

        // Bake tiles into single image
        bakeTiles();
    }

    private void buildCollisionAreas() {
        for (Layer layer : map.getLayers()) {
            if (layer.getName().equalsIgnoreCase("Collision")) {
                for (Obstacle obs : layer.getObjects()) {
                    obstacles.add(new Rectangle(obs.getX(), obs.getY(), obs.getWidth(), obs.getHeight()));
                }
            }
        }
    }

    private void loadSpriteSheets() {
        int tileWidth = map.getTilewidth();
        int tileHeight = map.getTileheight();

        int maxIndex = getMaxIndex();
        for (int x = 0; x <= maxIndex; x++) {
            tiles.add(null);
        }

        try {

            // Cycle through each tileset to load all tiles.
            for (TileSet ts : map.getTilesets()) {
                // Set index starting point to first global ID from JSON
                int curID = ts.getFirstgid();

                // Get tilesheet image
                BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(ts.getSource())));

                // Determine rows and column based on image size divided by tile size
                int cols = img.getWidth() / tileWidth;
                int rows = img.getHeight() / tileHeight;

                // cut up sprite sheet starting at top left and processing the image like reading a book.
                for (int y = 0; y < rows; y++) {
                    for (int x = 0; x < cols; x++) {
                        // Add each item to its predefined index in tiles array. This will eliminate the need to search for tile IDs later when rendering.
                        tiles.add(curID,
                                new Tile(
                                        scaleImage(img.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight),
                                                ScreenVar.TILE_SIZE.getValue(),
                                                ScreenVar.TILE_SIZE.getValue())));
                        curID++;
                    }
                }

            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private BufferedImage bakeTiles() {




        return null;
    }

    private BufferedImage scaleImage(BufferedImage orig, int width, int height) {
        BufferedImage scaledImg = new BufferedImage(width, height, orig.getType());
        Graphics2D g2 = scaledImg.createGraphics();
        g2.drawImage(orig, 0, 0, width, height, null);
        g2.dispose();
        return scaledImg;
    }


    private int getMaxIndex() {
        int max = 0;
        for (Layer layer : getMap().getLayers()) {
            if (layer.getMapData() != null) {
                for (int index : layer.getData()) {
                    max = Math.max(index, max);
                }
            }
        }
        return max;
    }

    /**
     * Render tiles on the screen by matching the value of each map location with a tile index.
     *
     * @param g2 2D Graphics Object
     */
    public void render(Graphics2D g2) {
        // DEBUG
        long start = System.nanoTime();

        for (Layer layer : getMap().getLayers()) {

            if (layer.getData() != null) {

                for (int x = layer.getX(); x < layer.getWidth(); x++) {
                    for (int y = layer.getY(); y < layer.getHeight(); y++) {
                        tileID = layer.getMapData()[y][x];

                        worldX = x * tileSize;
                        worldY = y * tileSize;
                        screenX = worldX - player.getWorldX() + player.getScreenX();
                        screenY = worldY - player.getWorldY() + player.getScreenY();

                        // If tile is visible on screen then render it.
                        if (worldX + tileSize > player.getWorldX() - player.getScreenX() &&
                                worldX - tileSize < player.getWorldX() + player.getScreenX() &&
                                worldY + tileSize > player.getWorldY() - player.getScreenY() &&
                                worldY - tileSize < player.getWorldY() + player.getScreenY() &&
                                tileID != 0){

                            g2.drawImage(tiles.get(tileID).getImage(), screenX, screenY, tileSize, tileSize, null);
                        }
                    }
                }

            }
        }

        // DEBUG
        long end = System.nanoTime();
        long passed = end - start;
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(passed), 10, 50);
        System.out.println(passed);

    }

    public Map getMap() {
        return map;
    }
}
