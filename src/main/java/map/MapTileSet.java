package map;

public class MapTileSet {
    public int firstgid;
    private String source;

    public MapTileSet(int firstgid, String source) {
        this.firstgid = firstgid;
        this.source = source;
    }

    public int getFirstgid() {
        return firstgid;
    }

    public String getName() {
        String[] stringArray = source.split("/");

        String[] finalString = stringArray[stringArray.length - 1].split("\\.");

        return finalString[0];
    }



}
