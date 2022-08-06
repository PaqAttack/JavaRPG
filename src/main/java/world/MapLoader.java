package world;

import com.google.gson.Gson;
import map.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
 * Class to handle loading map data from JSON files to the Map class.
 * This is currently setup to get map data from the Tiled map editor.
 */
public class MapLoader {
    String mapFileName;
    String jsonFileText;
    Gson gson;
    Map loadedMap;

    /**
     * Loads the provided JSON file and converts it to the appropriate Map, Layer and Obstacle data.
     *
     * @param fileName file name in resources to load. This must be a JSON file exported from Tiled.
     */
    public MapLoader(String fileName) {
        mapFileName = fileName;
        jsonFileText = loadMap(fileName);
        gson = new Gson();

        // Creates the Map Object.
        loadedMap = gson.fromJson(jsonFileText, Map.class);
    }

    /**
     * Loads JSON files into a text string and returns that data.
     *
     * @param fileName JSON file location in resources. This must be a Tiled exported map file.
     * @return A string containing the JSON file data.
     */
    private String loadMap(String fileName) {
        try {
            // Load the JSON file from resources and add it to a Buffered Reader.
            InputStream inputStream = getClass().getResourceAsStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(streamReader);

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

    public Map getMap() {
        return loadedMap;
    }
}
