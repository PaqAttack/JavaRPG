package world;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tiles.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TileSetLoader {

    private final ArrayList<TileSet> tileSets;

    public TileSetLoader() {
        tileSets = new ArrayList<>();
    }

    public void loadTileSet(String fileName) {
        // IMPORT JSON
        String jsonData = loadJSON(fileName);

        // Create Objects
        importJsonData(jsonData);
    }

    public ArrayList<TileSet> getTileSets() {
        return tileSets;
    }

    /**
     * Loads a JSON file and returns the contents as a string.
     *
     * @param fileName name of file to load.
     * @return String containing JSON data.
     */
    private String loadJSON(String fileName) {
        InputStream inputStream;
        InputStreamReader streamReader;
        BufferedReader br;

        try {
            // Load the JSON file from resources and add it to a Buffered Reader.
            inputStream = getClass().getResourceAsStream(fileName);
            assert inputStream != null;
            streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            br = new BufferedReader(streamReader);

            // Build a string.
            StringBuilder sb = new StringBuilder();
            for (String line; (line = br.readLine()) != null; ) {
                sb.append(line);
            }

            // Return the string data.
            return sb.toString();

        } catch (IOException e) {
            // Say NO to IOExceptions!
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads details from a JSON string into tile set objects.
     * @param json String containing JSON data.
     */
    private void importJsonData(String json) {
        JsonObject jsonObj = new Gson().fromJson(json, JsonObject.class);

        tileSets.add(new TileSet(jsonObj.get("image").toString().replace("\"", ""),
                Integer.parseInt(jsonObj.get("tilecount").toString()),
                Integer.parseInt(jsonObj.get("columns").toString()),
                Integer.parseInt(jsonObj.get("tileheight").toString()),
                Integer.parseInt(jsonObj.get("tilewidth").toString()),
                Integer.parseInt(jsonObj.get("imageheight").toString()),
                Integer.parseInt(jsonObj.get("imagewidth").toString())
        ));

        // Create an array from the tiles element of the JSON file.
        JsonArray tileArray = jsonObj.getAsJsonArray("tiles");

        // Import tile data directly into the class structure.
        TileData[] tileData = new Gson().fromJson(tileArray, TileData[].class);

        // Add tile data to the newest tileset
        tileSets.get(tileSets.size() - 1).setTileData(tileData);

        // Process tile data for use before continuing.
        // This includes:
        // 1. Fix file name
        // 2. Chop up tile sheet
        // 3. Set up animated frames
        tileSets.get(tileSets.size() - 1).processTileData();
    }

}
