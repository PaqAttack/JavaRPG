//package worldManagement;
//
//import core.ScreenVar;
//import entity.Player;
//import map.*;
//import tiles.Tile;
//import tiles.TileSet;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Objects;
//
//public class TileManager {
//
//    private ArrayList<TileSet> tileSets;
//
//    private final String tileSheetPath = "src/main/resources/tilesheets/";
//
//    private ArrayList<Rectangle> obstacles = new ArrayList<>();
//
//    private int tileID = 0;
//    private int worldX = 0;
//    private int worldY = 0;
//    private int screenX = 0;
//    private int screenY = 0;
//    private int tileSize;
//
//    // Temp Location for map file names
//    String myFile = "/JSON maps/TestMapwObstacles.json";
//    BufferedImage curScreen;
//
//    /**
//     * Constructs a Tile Manager.
//     */
//    public TileManager(ArrayList<TileSet> tileSets){
//        this.tileSets = tileSets;
//
//        // Load JSON File
////        MapLoader maploader = new MapLoader(myFile);
////        map = maploader.getMap();
//
////        tileSize = map.getTilewidth() * ScreenVar.SCALE.getValue();
//
//        // GSON imports my data as a 1D array and it would be much better if it were 2D so lets fix that.
//        //for (TileSet ts : tileSets) {
//        //    ts.loadSpriteSheets(tileSheetPath);
//        //}
//
//        // Build collision Array
//        //buildCollisionAreas();
//
//        // Cut Up Sprite Sheet and create a lot of Tiles.
//        //loadSpriteSheets();
//
//        // Bake tiles into single image
//        //bakeTiles();
//    }
//
//    /**
//     * Render tiles on the screen by matching the value of each map location with a tile index.
//     *
//     * @param g2 2D Graphics Object
//     */
//    public void render(Graphics2D g2) {
//        // DEBUG
//        long start = System.nanoTime();

//        for (Layer layer : getMap().getLayers()) {
//
//            if (layer.getData() != null) {
//
//                for (int x = layer.getX(); x < layer.getWidth(); x++) {
//                    for (int y = layer.getY(); y < layer.getHeight(); y++) {
//                        tileID = layer.getMapData()[y][x];
//
//                        worldX = x * tileSize;
//                        worldY = y * tileSize;
//                        screenX = worldX - player.getWorldX() + player.getScreenX();
//                        screenY = worldY - player.getWorldY() + player.getScreenY();
//
//                        // If tile is visible on screen then render it.
//                        if (worldX + tileSize > player.getWorldX() - player.getScreenX() &&
//                                worldX - tileSize < player.getWorldX() + player.getScreenX() &&
//                                worldY + tileSize > player.getWorldY() - player.getScreenY() &&
//                                worldY - tileSize < player.getWorldY() + player.getScreenY() &&
//                                tileID != 0){
//
//                            g2.drawImage(tiles.get(tileID).getImage(), screenX, screenY, tileSize, tileSize, null);
//                        }
//                    }
//                }
//
//            }
//        }
//
//        // DEBUG
//        long end = System.nanoTime();
//        long passed = end - start;
//        g2.setColor(Color.WHITE);
//        g2.drawString(String.valueOf(passed), 10, 50);
//        System.out.println(passed);
//
//    }
//}
