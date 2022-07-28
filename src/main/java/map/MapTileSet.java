package map;

public class MapTileSet {
    private int firstgid;
    private String source;

    /*
    Implementation of this class has not been done yet. This is a major conversion from a much simpler,
    currently implemented map system. There is currently no connection between this package and the rest of the game.
    */

    public MapTileSet(int firstgid, String source) {
        this.firstgid = firstgid;
        this.source = source;
    }

    public int getFirstgid() {
        return firstgid;
    }

    /*
     Source will be of the format:    "..\/TileSets\/SpriteSheet1.tsx"
     This needs to be converted to get the same file name but a  .png file in the resources folder.
     Since this class is created via GSON and not through the normal constructor I'm going to modify it here.
     */

    public String getSource() {
        String[] stringArray = source.split("/");
        String[] fileName = stringArray[stringArray.length - 1].split("\\.");
        String trimmedFileName = fileName[0];

        return "/tilesheets/" + trimmedFileName + ".png";
    }

}
