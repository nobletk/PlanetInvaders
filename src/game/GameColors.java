package game;

import java.awt.*;

public enum GameColors {
    BACKGROUND(new Color(0, 0, 20)),
    BUNKER(new Color(0, 255, 128)),
    ENEMY(new Color(0, 255, 255)),
    PLAYER(new Color(0, 255, 0)),
    TEXT(new Color(255, 255, 255)),
    UFO(new Color(255, 0, 255));

    private final Color color;

    GameColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
