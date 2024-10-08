package entityManager;

import entities.UFO;
import entities.enemy.Enemy;
import entities.enemy.EnemyA;
import entities.enemy.EnemyB;
import entities.enemy.EnemyC;
import game.GamePanel;
import game.GameScore;
import game.GameState;
import levels.LevelManager;
import utils.SoundPlayer;

import java.awt.*;
import java.util.Random;

public class EnemyManager {
    protected static int enemyRows = 5;
    protected static int enemyCols = 10;
    private final float INVASION_THRESHOLD;
    private GameScore score;
    private int[][] enemyGrid;
    private Enemy[][] enemies = new Enemy[enemyRows][enemyCols];
    private UFO ufo;
    private boolean ufoActive;
    private int ufoCooldown;
    private Random random;
    private float velX, velY, incVelX;
    private int moveSoundDelay;
    private SoundPlayer moveSound;

    public EnemyManager(float x, float y, float velX, float velY, float incVelX, int moveSoundDelay, LevelManager levelManager) {
        this.score = levelManager.getScore();
        this.velX = velX;
        this.velY = velY;
        this.incVelX = incVelX;
        this.moveSoundDelay = moveSoundDelay;
        this.random = new Random();
        this.ufoCooldown = random.nextInt(1000) + 3000;
        this.INVASION_THRESHOLD = 800f;

        enemyGrid = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
        };

        for (int i = 0; i < enemyGrid.length; i++) {
            for (int j = 0; j < enemyGrid[i].length; j++) {
                float xPos = j * 80 + x;
                float yPos = i * 50 + y;
                switch (enemyGrid[i][j]) {
                    case 1:
                        enemies[i][j] = new EnemyA(xPos, yPos, this.velX, this.velY, this.incVelX);
                        break;
                    case 2:
                        enemies[i][j] = new EnemyB(xPos, yPos, this.velX, this.velY, this.incVelX);
                        break;
                    case 3:
                        enemies[i][j] = new EnemyC(xPos, yPos, this.velX, this.velY, this.incVelX);
                        break;
                }
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null) {
                    e.render(g);
                }
            }
        }

        if (ufoActive && ufo != null) {
            ufo.render(g);
        }
    }

    public void update() {
        if (GameState.state == GameState.RUNNING && moveSound == null) playMoveSound();
        handleUFO();
        updateDeadEnemies();
        updateEnemyMovement();
        checkInvasion();
    }

    public void stopMoveSound() {
        if (moveSound != null) moveSound.stop();
    }

    private void updateDeadEnemies() {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null && e.isDead()) {
                    if (!e.isExploding() && !e.isReadyForRemoval()) {
                        e.destroy();
                        playDeathSound();
                        score.addPoints(e.getPoints());
                    }
                    if (e.isReadyForRemoval()) {
                        enemies[i][j] = null;
                    }
                }
            }
        }
    }

    private void playDeathSound() {
        SoundPlayer sound = new SoundPlayer("src/assets/sound/invaderkilled.wav");
        sound.setVolume(-10.0f);
        sound.play();
    }

    private void updateEnemyMovement() {
        boolean hitBounds = false;

        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null) {
                    e.update();
                    if (e.getX() <= 0 || e.getX() + e.getWidth() >= GamePanel.getScreenWidth()) {
                        hitBounds = true;
                    }
                }
            }
        }

        if (hitBounds) {
            changeEnemyDir();
        }
    }

    private void changeEnemyDir() {
        moveSoundDelay -= 1;
        if (moveSound != null) moveSound.setDelay(moveSoundDelay);

        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null) {
                    e.setIncVelX(-e.getIncVelX());
                    e.setVelX(-e.getVelX() + e.getIncVelX());
                    e.moveDownward();
                    e.update();
                }
            }
        }
    }

    private void checkInvasion() {
        for (int i = enemies.length - 1; i > 0; i--) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null) {
                    if (e.getY() == INVASION_THRESHOLD) {
                        GameState.state = GameState.GAME_OVER;
                        System.out.printf("invasion %d %d\n", i, j);
                        break;
                    }
                }
            }
        }
    }

    private void playMoveSound() {
        moveSound = new SoundPlayer("src/assets/sound/invadermove.wav");
        moveSound.setVolume(-10.0f);
        moveSound.setLoop(true);
        moveSound.setDelay(moveSoundDelay);
        moveSound.play();
    }

    private void handleUFO() {
        if (!ufoActive) {
            handleUFOCooldown();
        } else {
            updateAndCheckUFO();
        }
    }

    private void handleUFOCooldown() {
        if (!ufoActive) {
            ufoCooldown--;
            if (ufoCooldown <= 0) {
                spawnUFO();
                ufoActive = true;
                ufoCooldown = random.nextInt(1000) + 3000;
            }
        }
    }

    //screenWidth = 900, height = 1024
    private void spawnUFO() {
        boolean leftToRight = leftToRightUFO();
        float startX = leftToRight ? 0 : GamePanel.getScreenWidth();
        float velX = leftToRight ? 0.4f : -0.4f;
        ufo = new UFO(startX, 80, velX);
        ufo.playSound();
    }

    private void updateAndCheckUFO() {
        ufo.update();

        if (hasUFOExitedScreen() || ufo.isDead()) handleUFODestruction();
    }

    private boolean hasUFOExitedScreen() {
        return leftToRightUFO() ? ufo.getX() >= GamePanel.getScreenWidth() : ufo.getX() <= 0;
    }

    private void handleUFODestruction() {
        if (ufo.isDead()) {
            int bulletCount = score.getPlayerBulletCount();
            int mysteryPts = ufo.mysteryPts(bulletCount);
//            System.out.printf("ufoPts %d\tbulletCount %d\n", mysteryPts, bulletCount);
            score.addPoints(mysteryPts);
        }
        ufo.stopSound();
        ufoActive = false;
        ufo = null;
    }

    private boolean leftToRightUFO() {
        return random.nextBoolean();
    }

    public Enemy[][] getEnemies() {
        return enemies;
    }

    public UFO getUFO() {
        return ufo;
    }
}


