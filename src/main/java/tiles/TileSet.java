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

    public int getTileCount() {
        return tileCount;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void processTileData() {
        // Fix file name by cutting off the folder.
        image = fixFilename(image);

        // Chop Up sprite sheet
        loadSpriteSheets("/tilesheets/");

        tiledata = null;
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

    public void loadSpriteSheets(String filePath) {
        int curID = 0;
        String fileToLoad = filePath + image;

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
                                    ScreenVar.TILE_SIZE.getValue()),

                            curID);
                    curID++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTile(BufferedImage img, int curIndex) {
        tiles.add(new Tile(img));
        tiles.get(curIndex).setWalkable(!tiledata[curIndex].isBlocked());
        tiles.get(curIndex).setHeight(tileHeight);
        tiles.get(curIndex).setWidth(tileWidth);
        tiles.get(curIndex).setTilesetID(curIndex);
        if (tiledata[curIndex].hasAnimation()) {
            tiles.get(curIndex).setAnimationSequence(tiledata[curIndex].getAnimation());
            tiles.get(curIndex).setHasAnimation(true);
        }

    }

    private BufferedImage scaleImage(BufferedImage orig, int width, int height) {
        BufferedImage scaledImg = new BufferedImage(width, height, orig.getType());
        Graphics2D g2 = scaledImg.createGraphics();
        g2.drawImage(orig, 0, 0, width, height, null);
        g2.dispose();
        return scaledImg;
    }
}

