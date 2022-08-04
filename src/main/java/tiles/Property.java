package tiles;

public class Property {
    private boolean value;

    /**
     * This is a lazy implementation that supports only 1 custom property per tile.
     * This will need to be expanded.
     */
    public Property() {
        // THIS WILL ONLY BE CREATED BY GSON
    }

    public boolean isBlocked() {
        return value;
    }
}
