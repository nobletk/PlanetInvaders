package game;

import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private GameMenu gameMenu;
    private GameOver gameOver;
    private LevelManager levelManager;
    private boolean running;

    public Game() {
        init();
        startGameThread();
    }

    public void startGameThread() {
        running = true;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        int FPS_SET = 120;
        double timePerFrame = 1000000000.0 / FPS_SET;
        int UPS_SET = 200;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (running) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.printf("FPS: %d | UPS: %d\n", frames, updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void update() {
        switch (GameState.state) {
            case MENU:
                gameMenu.update();
                break;
            case RUNNING:
                levelManager.update();
                break;
            case LEVEL_COMPLETE:
                levelManager.update();
                gamePanel.getKeyInput().refreshInstances();
                break;
            case GAME_OVER:
            case WON:
                levelManager.gameOverCleanup();
                gameOver.update();
                if (KeyInput.restartPressed) {
                    levelManager.resetLevels();
                    gamePanel.getKeyInput().refreshInstances();
                    GameState.state = GameState.RUNNING;
                }
                break;
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU:
                gameMenu.render(g);
                break;
            case RUNNING:
            case GAME_OVER:
            case WON:
            case LEVEL_COMPLETE:
                levelManager.render(g);
                if (GameState.state == GameState.GAME_OVER || GameState.state == GameState.WON)
                    gameOver.render(g);
                break;
        }
    }

    private void init() {
        gameMenu = new GameMenu();
        levelManager = new LevelManager();
        gameOver = new GameOver();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }
}
