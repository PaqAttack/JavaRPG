package tiles;

public class TileData {
    private int id;
    private AnimationFrame[] animation;
    private Property[] properties;

    public TileData() {
        // ONLY CREATED BY GSON
    }

    public int getId() {
        return id;
    }

    public AnimationFrame[] getAnimation() {
        return animation;
    }

    public boolean hasAnimation() {
        if (animation != null) {
            return true;
        }
        return false;
    }

    public boolean isBlocked() {
        return properties[0].isBlocked();
    }
}
