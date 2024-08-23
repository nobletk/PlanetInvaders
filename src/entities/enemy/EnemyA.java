package entities.enemy;

import entities.Entity;

import java.awt.*;

public class EnemyA extends Entity implements Enemy {
    private final int[][] moving, original;
    private float velX, velY, incVelX;
    private int pts;
    private boolean dead, isMoving;
    private int animationCooldown;

    public EnemyA(float x, float y, float velX, float velY, float incVelX) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.incVelX = incVelX;
        this.pts = 10;
        this.dead = false;
        this.isMoving = true;
        this.animationCooldown = 0;

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
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    float xFill = x + j * getBlockWidth();
                    float yFill = y + i * getBlockHeight();
                    g.setColor(Color.RED);
                    g.fillRect((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
                }
            }
        }
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

    private void animate() {
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
    public int getPoints() {
        return pts;
    }
}
