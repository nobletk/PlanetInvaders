package game;

public enum GameState {
    MENU,
    RUNNING,
    LEVEL_COMPLETE,
    GAME_OVER;

    public static GameState state = MENU;
}
