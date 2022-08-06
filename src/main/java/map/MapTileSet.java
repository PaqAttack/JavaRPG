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

    /**
     * This will return the file name without a file extension (.png) or the directory information such as "xxx/".
     *
     * @return String of file name. This will allow us to connect the JSON and png files and associate them correctly.
     */
    public String getName() {
        String[] stringArray = source.split("/");

        String[] finalString = stringArray[stringArray.length - 1].split("\\.");

        return finalString[0];
    }


}
