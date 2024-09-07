package game;

public enum GameState {
    MENU,
    RUNNING,
    LEVEL_COMPLETE,
    WON,
    GAME_OVER;

    public static GameState state = MENU;
}
