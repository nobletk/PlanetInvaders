package entities.enemy;

import entities.Entity;
import game.GameColors;

import java.awt.*;

public class EnemyA extends Entity implements Enemy {
    private final int[][] moving, original;
    private final int pts;
    private final Color color;
    private float velX, velY, incVelX;
    private boolean dead, isMoving, exploding, readyForRemoval;
    private int animationCooldown, explosionDuration;

    public EnemyA(float x, float y, float velX, float velY, float incVelX) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.incVelX = incVelX;
        this.pts = 10;
        this.dead = false;
        this.isMoving = true;
        this.exploding = false;
        this.animationCooldown = 0;
        this.explosionDuration = 0;
        this.readyForRemoval = false;
        this.color = GameColors.ENEMY.getColor();

        this.original = new int[][]{
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //legs
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        };

        this.grid = original;

        this.moving = new int[][]{
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //legs
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        };

    }

    @Override
    public void render(Graphics g) {
        if (explosionDuration > 0) {
            explode(g);
        } else {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 1) {
                        float xFill = x + j * getBlockWidth();
                        float yFill = y + i * getBlockHeight();
                        g.setColor(color);
                        g.fillRect((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
                    }
                }
            }
        }
    }

    @Override
    public void explode(Graphics g) {
        int[][] explosion = new int[][]{
                {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
        };

        for (int i = 0; i < explosion.length; i++) {
            for (int j = 0; j < explosion[i].length; j++) {
                if (explosion[i][j] == 1) {
                    float xFill = x + j * getBlockWidth();
                    float yFill = y + i * getBlockHeight();
                    g.setColor(color);
                    g.fillRect((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
                }
            }
            explosionDuration--;
            if (explosionDuration <= 0) {
                exploding = false;
                readyForRemoval = true;
            }
        }
    }

    @Override
    public void destroy() {
        if (!exploding) {
            exploding = true;
            explosionDuration = 60;
        }
    }

    @Override
    public boolean isReadyForRemoval() {
        return readyForRemoval;
    }

    @Override
    public void update() {
        if (animationCooldown > 0) animationCooldown--;
        sideMovement();
        animate();
    }

    @Override
    public void sideMovement() {
        x += velX;
    }

    @Override
    public void animate() {
        if (animationCooldown == 0) {
            if (isMoving) {
                this.grid = moving;
            } else {
                this.grid = original;
            }
            isMoving = !isMoving;
            animationCooldown = 175;
        }
    }

    @Override
    public float getVelX() {
        return velX;
    }

    @Override
    public void setVelX(float velX) {
        this.velX = velX;
    }

    @Override
    public float getIncVelX() {
        return incVelX;
    }

    @Override
    public void setIncVelX(float incVelX) {
        this.incVelX = incVelX;
    }

    @Override
    public void moveDownward() {
        y += velY;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public boolean isExploding() {
        return exploding;
    }

    @Override
    public int getPoints() {
        return pts;
    }
}
