package tiles;

public class AnimationSequence {
    private int duration;
    private int tileid;

    public AnimationSequence() {
        // ONLY CREATED BY GSON
    }

    /**
     * Returns duration in milliseconds to display a TileID
     * @return int representing milliseconds to display this TileID.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * TileID to display at this animation sequence point.
     * @return int representing tileID
     */
    public int getTileid() {
        return tileid;
    }
}
