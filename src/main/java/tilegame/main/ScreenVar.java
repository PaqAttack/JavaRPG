package paquin.tilegame.main;

public enum ScreenVar {
    ORIG_TILE_SIZE(16),
    SCALE(3),
    TILE_SIZE(tileSize()),
    MAX_SCREEN_COL(16),
    MAX_SCREEN_ROW(12),
    SCREEN_WIDTH(screenWidth()),
    SCREEN_HEIGHT(screenHeight());

    ScreenVar(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static int tileSize() {
        return ORIG_TILE_SIZE.getValue() * SCALE.getValue();
    }

    private static int screenWidth() {
        return ScreenVar.TILE_SIZE.getValue() * ScreenVar.MAX_SCREEN_COL.getValue();
    }

    private static int screenHeight() {
        return ScreenVar.TILE_SIZE.getValue() * ScreenVar.MAX_SCREEN_ROW.getValue();
    }

    private int value;
}

