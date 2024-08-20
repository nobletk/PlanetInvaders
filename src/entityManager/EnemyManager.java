package entityManager;

import entities.enemy.Enemy;
import entities.enemy.EnemyA;
import entities.enemy.EnemyB;
import entities.enemy.EnemyC;
import game.Game;
import game.GamePanel;
import game.GameScore;

import java.awt.*;

public class EnemyManager {
    protected static int enemyRows = 5;
    protected static int enemyCols = 10;
    private Game game;
    private GameScore score;
    private int[][] enemyGrid;
    private Enemy[][] enemies = new Enemy[enemyRows][enemyCols];
    private float velX, velY, incVelX;

    public EnemyManager(float x, float y, Game game) {
        this.game = game;
        this.score = game.getScore();
        this.velX = 0.5f;
        this.velY = 5f;
        this.incVelX = 0.03f;

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
    }

    public void update() {
        updateDeadEnemies();
        updateEnemyMovement();
    }

    private void updateDeadEnemies() {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null && e.isDead()) {
                    e.destroy();
                    score.addPoints(e.getPoints());
                    enemies[i][j] = null;
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
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null) {
                    //TODO: gameOver when hitting bunkers
                    e.setIncVelX(-e.getIncVelX());
                    e.setVelX(-e.getVelX() + e.getIncVelX()); //-10-5; 10+5
                    e.moveDownward();
                    e.update();
                }
            }
        }
    }

    public Enemy[][] getEnemies() {
        return enemies;
    }
}


