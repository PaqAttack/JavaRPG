package map;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MapLoader {
    String mapFileName;
    String jsonFileText;
    Gson gson;

    public static void main(String[] args) {
        String myFile = "/JSON maps/Test Map.json";
        MapLoader maploader = new MapLoader(myFile);

    }

    public MapLoader(String fileName) {
        mapFileName = fileName;
        jsonFileText = loadMap(fileName);

        gson = new Gson();

        Map m1 = gson.fromJson(jsonFileText, Map.class);
    }

    private String loadMap(String fileName) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(streamReader);

            StringBuilder sb = new StringBuilder();
            for (String line; (line = br.readLine()) != null;) {
                sb.append(line);
            }

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
