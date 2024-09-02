package game;

import java.awt.*;

public enum GameColors {
    BACKGROUND(new Color(0, 0, 20)),
    BUNKER(new Color(0, 255, 128)),
    ENEMY(new Color(0, 255, 255)),
    PLAYER(new Color(0, 255, 0)),
    TEXT(new Color(255, 255, 255)),
    UFO(new Color(255, 0, 255)),
    BLUEWHITE(new Color(200, 200, 255)),
    YELLOWWHITE(new Color(255, 255, 200)),
    REDWHITE(new Color(255, 180, 180)),
    PLANET1(new Color(255, 105, 30)),
    PLANET2(new Color(255, 180, 150)),
    PLANET3(new Color(255, 180, 180)),
    PLANET4(new Color(255, 255, 255, 80)),
    PLANET5(new Color(255, 255, 255, 0));

    private final Color color;

    GameColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
