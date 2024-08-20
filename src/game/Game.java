package game;

import entities.Player;
import entityManager.BulletManager;
import entityManager.BunkerManager;
import entityManager.EnemyManager;
import entityManager.TaskManager;

import java.awt.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Player player;
    private BulletManager bulletManager;
    private EnemyManager enemyManager;
    private BunkerManager bunkerManager;
    private TaskManager taskManager;
    private GameScore score;

    public Game() {
        init();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        startGameThread();
    }

    public void startGameThread() {
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

        while (true) {
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
        player.update();
        bulletManager.update();
        enemyManager.update();
        bunkerManager.update();
        taskManager.update();
        score.update();
    }

    public void render(Graphics g) {
        player.render(g);
        bulletManager.render(g);
        enemyManager.render(g);
        bunkerManager.render(g);
        score.render(g);
    }

    private void init() {
        score = new GameScore(this);
        player = new Player(450, 900);
        bunkerManager = new BunkerManager(70, 820, this);
        enemyManager = new EnemyManager(50, 200, this);
        bulletManager = new BulletManager(this);
        taskManager = new TaskManager(this);
    }

    public Player getPlayer() {
        return player;
    }

    public BulletManager getBulletManager() {
        return bulletManager;
    }

    public BunkerManager getBunkerManager() {
        return bunkerManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public GameScore getScore() {
        return score;
    }
}
