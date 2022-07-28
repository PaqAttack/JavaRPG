package worldManagement;

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

public class TileSetLoader {

    private ArrayList<TileSet> tileSets;

    public TileSetLoader() {
        tileSets = new ArrayList<>();
    }

    public void loadTileSet(String fileName) {
        // IMPORT JSON
        String jsonData = loadJSON(fileName);

        // Create Objects
        importJOSNdata(jsonData);
    }

    public ArrayList<TileSet> getTileSets() {
        return tileSets;
    }

    private String loadJSON(String fileName) {
        InputStream inputStream;
        InputStreamReader streamReader;
        BufferedReader br;

        try {
            // Load the JSON file from resources and add it to a Buffered Reader.
            inputStream = getClass().getResourceAsStream(fileName);
            streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            br = new BufferedReader(streamReader);

            // Build a string.
            StringBuilder sb = new StringBuilder();
            for (String line; (line = br.readLine()) != null;) {
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

    private void importJOSNdata(String json) {
        JsonObject jsonObj = new Gson().fromJson(json, JsonObject.class);

        tileSets.add(new TileSet(jsonObj.get("image").toString().replace("\"", ""),
                Integer.parseInt(jsonObj.get("tilecount").toString()),
                Integer.parseInt(jsonObj.get("columns").toString()),
                Integer.parseInt(jsonObj.get("tileheight").toString()),
                Integer.parseInt(jsonObj.get("tilewidth").toString()),
                Integer.parseInt(jsonObj.get("imageheight").toString()),
                Integer.parseInt(jsonObj.get("imagewidth").toString())
                ));

        JsonArray tileArray = jsonObj.getAsJsonArray("tiles");
        TileData[] tiledata = new Gson().fromJson(tileArray, TileData[].class);
        tileSets.get(tileSets.size() - 1).setTileData(tiledata);

        tileSets.get(tileSets.size() - 1).processTileData();
    }

}
