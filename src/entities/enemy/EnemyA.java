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
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        };

    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    float xFill = x + j * getBlockWidth();
                    float yFill = y + i * getBlockHeight();
                    g.setColor(Color.RED);
                    g.fillRect((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
                    g2D.setColor(Color.black);
                    g2D.draw(getBounds(xFill, yFill, getBlockWidth(), getBlockHeight()));
                }
            }
        }
    }

    //TODO: The aliens should move left to right across the screen,
    // stepping down every time they touch the edge of the screen.
    // They should also move a little faster each time they step down the screen.
    // The aliens should drop a bomb that moves down the screen towards the bottom occasionally.
    // Create 2 loops to populate it with different enemy types.
    @Override
    public void update() {
        if (animationCooldown > 0) animationCooldown--;
        sideMovement();
        animate();
    }

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
            animationCooldown = 150;
        }
    }

    @Override
    public float getVelX() {
        return velX;
    }

    @Override
    public void setVelX(float i) {
        velX = i;
    }

    @Override
    public float getIncVelX() {
        return incVelX;
    }

    @Override
    public void setIncVelX(float i) {
        incVelX = i;
    }

    @Override
    public void moveDownward() {
        y += velY;
    }

    @Override
    public Rectangle getBounds(float x, float y, int w, int h) {
        //TODO: add speed when movement is implemented
        int rectangleX = (int) x;
        int rectangleY = (int) y;
        int rectangleWidth = w;
        int rectangleHeight = h;

        return new Rectangle(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean b) {
        dead = b;
    }

    @Override
    public int getPoints() {
        return pts;
    }
}
