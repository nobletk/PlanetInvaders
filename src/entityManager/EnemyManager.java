package entityManager;

import entities.UFO;
import entities.enemy.*;
import game.Game;
import game.GamePanel;
import game.GameScore;
import game.SoundPlayer;

import java.awt.*;
import java.util.Random;

public class EnemyManager {
    protected static int enemyRows = 5;
    protected static int enemyCols = 10;
    private Game game;
    private GameScore score;
    private int[][] enemyGrid;
    private Enemy[][] enemies = new Enemy[enemyRows][enemyCols];
    private UFO ufo;
    private boolean ufoActive;
    private int ufoCooldown;
    private Random random;
    private float velX, velY, incVelX;
    private int delayMoveSound;
    private SoundPlayer moveSound;

    public EnemyManager(float x, float y, Game game) {
        this.game = game;
        this.score = game.getScore();
        this.velX = 0.2f;
        this.velY = 10f;
        this.incVelX = 0.03f;
        this.delayMoveSound = 700;
        this.random = new Random();
        this.ufoCooldown = random.nextInt(1000) + 3000;

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
                        enemies[i][j] = new EnemyA(xPos, yPos, velX, velY, incVelX);
                        break;
                    case 2:
                        enemies[i][j] = new EnemyB(xPos, yPos, velX, velY, incVelX);
                        break;
                    case 3:
                        enemies[i][j] = new EnemyC(xPos, yPos, velX, velY, incVelX);
                        break;
                }
            }
        }

        moveSound = new SoundPlayer("src/assets/sound/invadermove.wav");
        moveSound.setVolume(-10.0f);
        moveSound.setLoop(true);
        moveSound.setDelay(delayMoveSound);
        moveSound.play();
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
        handleUFO();
        updateDeadEnemies();
        updateEnemyMovement();
    }

    private void updateDeadEnemies() {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null && e.isDead()) {
                    if (!e.isExploding() && !e.isReadyForRemoval()) {
                        e.destroy();
                        SoundPlayer sound = new SoundPlayer("src/assets/sound/invaderkilled.wav");
                        sound.setVolume(-10.0f);
                        sound.play();
                        score.addPoints(e.getPoints());
                    }
                    if (e.isReadyForRemoval()) {
                        enemies[i][j] = null;
                    }
                }
            }
        }
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
        delayMoveSound -= 1;
        moveSound.setDelay(delayMoveSound);

        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null) {
                    //TODO: gameOver when hitting bunkers
                    e.setIncVelX(-e.getIncVelX());
                    e.setVelX(-e.getVelX() + e.getIncVelX());
                    e.moveDownward();
                    e.update();
                }
            }
        }
    }

    private void handleUFO() {
        if (!ufoActive) {
            ufoCooldown--;
            if (ufoCooldown <= 0) {
                ufo = new UFO(900, 80, -0.4f);
                ufoActive = true;
                ufoCooldown = random.nextInt(1000) + 3000;
            }
        } else {
            ufo.update();
            if (ufo.getX() <= 0 || ufo.isDead()) {
                if (ufo.isDead()) score.addPoints(ufo.getPoints());
                ufo.update();
                ufoActive = false;
                ufo = null;
            }
        }
    }

    public Enemy[][] getEnemies() {
        return enemies;
    }

    public UFO getUFO() {
        return ufo;
    }
}


