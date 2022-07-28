package tiles;

public class Property {
    private boolean value;

    public Property() {
        // THIS WILL ONLY BE CREATED BY GSON
    }

    public boolean isBlocked() {
        return value;
    }
}
