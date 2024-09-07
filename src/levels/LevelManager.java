package levels;

import entities.Player;
import entities.enemy.Enemy;
import entityManager.AmmoManager;
import entityManager.BunkerManager;
import entityManager.EnemyManager;
import entityManager.TaskManager;
import game.GameBackground;
import game.GameColors;
import game.GameScore;
import game.GameState;
import physics.Collision;
import utils.FontLoader;

import java.awt.*;

import static utils.GraphicsUtils.drawCenteredText;

public class LevelManager {
    private final Font levelFont;
    private final Color textColor;
    private final int MAX_LEVEL, moveSoundDelay;
    private final float playerInitX, playerInitY, bunkerInitX, bunkerInitY, enemyInitX, enemyInitY, enemyInitVelX, enemyInitVelY, enemyInitIncVelX;
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
        this.playerInitX = 400f;
        this.playerInitY = 900f;
        this.bunkerInitX = 70f;
        this.bunkerInitY = 820;
        this.enemyInitX = 50f;
        this.enemyInitY = 200f;
        this.enemyInitVelX = 0.2f;
        this.enemyInitVelY = 5f;
        this.enemyInitIncVelX = 0.03f;
        this.moveSoundDelay = 700;
        this.currentLevel = 1;
        this.MAX_LEVEL = 10;
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
        player = new Player(playerInitX, playerInitY);
        bunkerManager = new BunkerManager(bunkerInitX, bunkerInitY);
        enemyManager = new EnemyManager(enemyInitX, enemyInitY, enemyInitVelX, enemyInitVelY, enemyInitIncVelX, moveSoundDelay, this);
        ammoManager = new AmmoManager(this);
        taskManager = new TaskManager(this);
    }

    private void nextLevel() {
        System.out.printf("starting level %d\n", currentLevel);
        initNextLevel(currentLevel);
        GameState.state = GameState.RUNNING;
    }

    private void initNextLevel(int level) {
        // lower the initial invaders spawn from 200f + 50f * 4 = 400f
        // last level should be from 500f:700f
        // invasion/game over @ 780f
        float newEnemyY = 33 * (level - 1) + 200;
        float newEnemyVelX = enemyInitVelX * level;
        int newMoveSoundDelay = moveSoundDelay - 60 * (level - 1);
        player = new Player(playerInitX, playerInitY);
        enemyManager = new EnemyManager(enemyInitX, newEnemyY, newEnemyVelX, enemyInitVelY, enemyInitIncVelX, newMoveSoundDelay, this);
        ammoManager = new AmmoManager(this);
        taskManager = new TaskManager(this);
    }

    private void checkLevelCompletion() {
        if (levelComplete && currentLevel < MAX_LEVEL) {
            GameState.state = GameState.LEVEL_COMPLETE;
            currentLevel++;
            levelText = "level " + currentLevel;
            nextLevelDelay = 120;
            levelComplete = false;
        }
        if (levelComplete && currentLevel == MAX_LEVEL) {
            System.out.println("you won");
            GameState.state = GameState.WON;
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
