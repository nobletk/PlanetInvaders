package entities.enemy;

import entities.Entity;

import java.awt.*;

public class EnemyB extends Entity implements Enemy {
    private final int[][] moving, original;
    private float velX, velY, incVelX;
    private int pts;
    private boolean dead, isMoving;
    private int animationCooldown;

    public EnemyB(float x, float y, float velX, float velY, float incVelX) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.incVelX = incVelX;
        this.gridWidth = 16;
        this.gridHeight = 10;
        this.blockSize = 3;
        this.pts = 20;
        this.dead = false;
        this.isMoving = true;
        this.animationCooldown = 0;

        this.original = new int[][]{
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1},
                {1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
        };

        this.grid = original;

        this.moving = new int[][]{
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0},
        };
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if (grid[i][j] == 1) {
                    float xFill = x + j * blockSize;
                    float yFill = y + i * blockSize;
                    g.setColor(Color.blue);
                    g.fillRect((int) xFill, (int) yFill, blockSize, blockSize);
                    g2D.setColor(Color.darkGray);
                    g2D.draw(getBounds(xFill, yFill, blockSize, blockSize));
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

    public void updateBlock(int row, int col) {
        System.out.printf("Remove %d, %d\n", row, col);
        grid[row][col] = 0;
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

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean b) {
        this.dead = b;
    }

    @Override
    public int getPoints() {
        return pts;
    }
}
