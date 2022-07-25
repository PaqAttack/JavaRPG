package core;

public enum WorldVar {
    MAX_WORLD_COL(100),
    MAX_WORLD_ROW(100),
    MAX_WORLD_WIDTH(worldWidth()),
    MAX_WORLD_HEIGHT(worldHeight());

    WorldVar(int value) {
        this.value = value;
    }

    public static int worldWidth() {
        return ScreenVar.TILE_SIZE.getValue() * WorldVar.MAX_WORLD_COL.getValue();
    }

    public static int worldHeight() {
        return ScreenVar.TILE_SIZE.getValue() * WorldVar.MAX_WORLD_ROW.getValue();
    }

    public int getValue() {
        return value;
    }

    private int value;
}
