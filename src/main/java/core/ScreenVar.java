package core;

/**
 * Enum based game properties.
 * This was an experiment and I don't like it.
 * I will transition to using a file that's loaded on startup for this.
 */
public enum ScreenVar {
    // Individual un-scaled tile size
    ORIG_TILE_SIZE(16),

    // Scale to multiply tile size by
    SCALE(3),

    // resulting tile size
    TILE_SIZE(tileSize()),

    // Number of columns of tile to display on screen.
    MAX_SCREEN_COL(24),     // orig 16

    // Number of rows of tile to display on screen.
    MAX_SCREEN_ROW(16),     //orig 12

    // Number of pixels wide for the entire window
    SCREEN_WIDTH(screenWidth()),

    // Number of pixels high for the entire window
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

