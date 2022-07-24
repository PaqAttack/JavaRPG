package map;

import com.google.gson.Gson;

public class MapLoader {
    String mapFileName;
    String jsonFileText;
    Gson gson;

    public MapLoader(String fileName) {
        mapFileName = fileName;

        gson = new Gson();
        gson.fromJson(jsonFileText, Map.class);
    }

}
