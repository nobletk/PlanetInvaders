package levels;

import entities.Player;
import entities.enemy.Enemy;
import entityManager.AmmoManager;
import entityManager.BunkerManager;
import entityManager.EnemyManager;
import entityManager.TaskManager;
import fontLoader.FontLoader;
import game.*;

import java.awt.*;

public class LevelManager {
    private final Font levelFont;
    private final Color textColor;
    private GameScore score;
    private Player player;
    private AmmoManager ammoManager;
    private BunkerManager bunkerManager;
    private EnemyManager enemyManager;
    private TaskManager taskManager;
    private GameBackground background;
    private int currentLevel, nextLevelDelay;
    private String levelText;
    private boolean levelComplete;

    public LevelManager() {
        this.currentLevel = 1;
        initFirstLevel();

        this.levelFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 40f);
        this.textColor = GameColors.TEXT.getColor();
    }

    public void render(Graphics g) {
        background.render(g);
        player.render(g);
        ammoManager.render(g);
        enemyManager.render(g);
        bunkerManager.render(g);
        score.render(g);

        //render level text + currentLevel num
        if (GameState.state == GameState.LEVEL_COMPLETE) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(textColor);
            g2d.setFont(levelFont);
            drawCenteredText(g2d, levelText, 250);
        }
    }

    public void update() {
        switch (GameState.state) {
            case RUNNING:
                updateEntities();
                checkAllEnemiesDead();
                checkLevelCompletion();
                break;
            case GameState.LEVEL_COMPLETE:
                handleLevelCompletion();
                break;
        }
    }

    private void updateEntities() {
        score.update();
        player.update();
        ammoManager.update();
        enemyManager.update();
        bunkerManager.update();
        taskManager.update();
    }

    private void handleLevelCompletion() {
        nextLevelDelay--;
        if (nextLevelDelay <= 0) nextLevel();
    }

    public void resetLevels() {
        score.resetScore();
        initEntities();
    }

    public void gameOverCleanup() {
        if (enemyManager.getUFO() != null) enemyManager.getUFO().stopSound();
        enemyManager.stopMoveSound();
        score.updateHighScore();
        currentLevel = 1;
    }

    private void initFirstLevel() {
        background = new GameBackground();
        score = new GameScore();
        initEntities();
    }

    private void initEntities() {
        player = new Player(400, 900);
        bunkerManager = new BunkerManager(70, 820);
        enemyManager = new EnemyManager(50, 200, this);
        ammoManager = new AmmoManager(this);
        taskManager = new TaskManager(this);
    }

    public void nextLevel() {
        System.out.printf("starting level %d\n", currentLevel);
        initNextLevel(currentLevel);
        GameState.state = GameState.RUNNING;
    }

    private void initNextLevel(int level) {
        // lower the initial invaders spawn
        // may be increase the initial speed
        player = new Player(400, 900);
        bunkerManager = new BunkerManager(70, 820);
        enemyManager = new EnemyManager(50, 200, this);
        ammoManager = new AmmoManager(this);
        taskManager = new TaskManager(this);
    }

    private void checkLevelCompletion() {
        if (levelComplete) {
            GameState.state = GameState.LEVEL_COMPLETE;
            currentLevel++;
            levelText = "level " + currentLevel;
            nextLevelDelay = 120;
            levelComplete = false;
        }
    }

    private void checkAllEnemiesDead() {
        Enemy[][] enemies = enemyManager.getEnemies();
        boolean allEnemiesDead = true;

        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                if (enemies[i][j] != null) {
                    allEnemiesDead = false;
                    break;
                }
            }
            if (!allEnemiesDead) break;
        }

        if (allEnemiesDead && enemyManager.getUFO() == null) {
            System.out.printf("level %d complete\n", currentLevel);
            enemyManager.stopMoveSound();
            levelComplete = true;
        }
    }

    private void drawCenteredText(Graphics2D g2d, String text, int y) {
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int textWidth = metrics.stringWidth(text);
        int x = (GamePanel.getScreenWidth() - textWidth) / 2;
        g2d.drawString(text, x, y);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public AmmoManager getAmmoManager() {
        return ammoManager;
    }

    public BunkerManager getBunkerManager() {
        return bunkerManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public GameScore getScore() {
        return score;
    }
}
