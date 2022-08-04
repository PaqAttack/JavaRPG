package tiles;

import core.ScreenVar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TileSet {
    private String image;
    private int tileCount;
    private int columns;
    private int tileHeight, tileWidth;
    private int imageHeight, imageWidth;
    private TileData[] tiledata;
    private ArrayList<Tile> tiles;

    public TileSet(String image, int tileCount, int columns, int tileHeight, int tileWidth, int imageHeight, int imageWidth) {
        this.image = image;
        this.tileCount = tileCount;
        this.columns = columns;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;

        tiles = new ArrayList<>();
    }

    /**
     * Processes tile set data and preps it for loading into a map.
     */
    public void processTileData() {
        // Fix file name by cutting off the folder.
        image = fixFilename(image);

        // Chop Up sprite sheet
        chopTileSheet();

        // Add images into array list in animation sequence.
        // IDs will no longer correspond to the correct tiles once map tile sets are generated.
        // This will preserve the correct data.
        setupAnimationTiles();

        tiledata = null;
    }

    /**
     * Adds all animation tiles to a separate array.
     * This enables us to display the animation tile sequences as normal on top of the static concatenated image.
     */
    private void setupAnimationTiles() {
        for (Tile tile : tiles) {
            if (tile.hasAnimation()){
                for (AnimationFrame as : tile.getAnimationSequence()) {
                    BufferedImage tempImg = tiles.get(as.getTileid()).getImage();
                    tile.getAnimationImages().add(tempImg);
                }
            }
        }
    }

    /**
     * Returns file name "xxxxx.png" from "/dir/dir/xxxxx.png"
     * @param fileName original file name
     * @return file name as string without directories
     */
    private String fixFilename(String fileName) {
        String[] stringArray = fileName.split("/");
        return stringArray[stringArray.length - 1];
    }

    /**
     * chops up tilesets and creates tiles from them.
     */
    public void chopTileSheet() {
        int curID = 0;
        String fileToLoad = "/tilesheets/" + image;

        try {
            // Get tilesheet image
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(fileToLoad)));

            // cut up sprite sheet starting at top left and processing the image like reading a book.
            for (int y = 0; y < getRows(); y++) {
                for (int x = 0; x < getColumns(); x++) {

                    createTile(
                            scaleImage(
                                    img.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight),
                                    ScreenVar.TILE_SIZE.getValue(),
                                    ScreenVar.TILE_SIZE.getValue()));
                    curID++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new tile and add all its data.
     * @param img Buffered Image for the tile to use.
     */
    private void createTile(BufferedImage img) {
        tiles.add(new Tile(img));
        int lastIndex = tiles.size()-1;
        tiles.get(lastIndex).setWalkable(!tiledata[lastIndex].isBlocked());
        tiles.get(lastIndex).setHeight(tileHeight);
        tiles.get(lastIndex).setWidth(tileWidth);
        tiles.get(lastIndex).setTilesetID(lastIndex);
        if (tiledata[lastIndex].hasAnimation()) {
            tiles.get(lastIndex).setAnimationSequence(tiledata[lastIndex].getAnimation());
            tiles.get(lastIndex).setHasAnimation(true);
        }
    }

    /**
     * Scales the image to the desired size
     * @param orig original buffered image
     * @param width desired width in pixels
     * @param height desired height in pixels
     * @return scaled buffered image
     */
    private BufferedImage scaleImage(BufferedImage orig, int width, int height) {
        BufferedImage scaledImg = new BufferedImage(width, height, orig.getType());
        Graphics2D g2 = scaledImg.createGraphics();
        g2.drawImage(orig, 0, 0, width, height, null);
        g2.dispose();
        return scaledImg;
    }

    public void setTileData(TileData[] tiles) {
        this.tiledata = tiles;
    }

    public String getImageFileName() {
        return image;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return imageHeight / tileHeight;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}

