package tiles;

import core.ScreenVar;
import core.WorldVar;
import entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    // This tracks the number of tiles set up in the game
    private final int NUM_OF_TILES = 4;

    // This array will hold all tiles.
    private final Tile[] tile;

    // This will store the currently loaded map.
    private int[][] map;

    private Player player;
    /**
     * Constructs a Tile Manager.
     */
    public TileManager(Player player) {
        this.player = player;

        // Initialize arrays.
        tile = new Tile[NUM_OF_TILES];
        map = new int[WorldVar.MAX_WORLD_COL.getValue()][WorldVar.MAX_WORLD_ROW.getValue()];

        // Load Tile Images
        getTileImage();

        // Levels available to load.
        String level01 = "/maps/Level 1.txt";
        String level02 = "/maps/Level 2.txt";

        // Loads level into map.
        loadMap(level02);
    }

    /**
     * Creates tile objects and loads their data.
     */
    public void getTileImage() {
        try {
            // BLANK
            tile[0] = new Tile(false, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/BLANK.png"))));

            // DIRT
            tile[1] = new Tile(false, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/dirt.png"))));

            // GRASS
            tile[2] = new Tile(true, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png"))));

            // WATER
            tile[3] = new Tile(false, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a new map from the text file passed in.
     * @param levelPath String containing the file path for the level to load.
     */
    public void loadMap(String levelPath) {
        int row = 0;

        try {
            InputStream is = getClass().getResourceAsStream(levelPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Reads data from text file. The quanity of data is unknown and up to the set max world row/colum data will be read.
            while (row < WorldVar.MAX_WORLD_ROW.getValue()) {
                // Moves the next line of text from the Buffered reader to the Line string.
                String line = br.readLine();

                // If this line is null then the end of the file has been reached and the while loop is broken.
                if (line == null) {
                    break;
                }

                // split up the line and add each value to the "numbers" array
                String[] numbers = line.split(" ");

                // Store the quanity of entries in the "numbers" array for iteration.
                int columns = numbers.length;

                // Iterate through the "numbers" array until there is no more data or the max size of the world is reached. Extra data is ignored.
                for (int col = 0; col < Math.min(columns, WorldVar.MAX_WORLD_COL.getValue()); col++) {
                    // Set the map value for the appropriate location to be the value read in. This value matches the tile index.
                    map[col][row] = Integer.parseInt(numbers[col]);
                }
                // When a row has been completed move to the next one.
                row++;
            }

            // Close input stream and buffered reader.
            is.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Render tiles on the screen by matching the value of each map location with a tile index.
     * @param g2 2D Graphics Object
     */
    public void render(Graphics2D g2) {
        int tileSize = ScreenVar.TILE_SIZE.getValue();


        for (int x = 0; x < WorldVar.MAX_WORLD_COL.getValue(); x++) {
            for (int y = 0; y < WorldVar.MAX_WORLD_ROW.getValue(); y++) {
                int tileID = map[x][y];

                int worldX = x * tileSize;
                int worldY = y * tileSize;
                int screenX = worldX - player.getWorldX() + player.getScreenX();
                int screenY = worldY - player.getWorldY() + player.getScreenY();

                // If tile is visible on screen then render it.
                if (worldX + tileSize > player.getWorldX() - player.getScreenX() &&
                    worldX - tileSize < player.getWorldX() + player.getScreenX() &&
                    worldY + tileSize > player.getWorldY() - player.getScreenY() &&
                    worldY - tileSize < player.getWorldY() + player.getScreenY()) {

                    g2.drawImage(tile[tileID].getImage(), screenX, screenY, tileSize, tileSize, null);
                }
            }
        }
    }
}
