package entities;

import java.awt.*;

public abstract class Entity {

    protected int[][] grid;
    protected float x, y;
    protected int blockWidth, blockHeight;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
        this.blockHeight = 4;
        this.blockWidth = 3;
    }

    public void render(Graphics g) {}

    public void update() {}

    public Rectangle getBounds(float x, float y, int w, int h) {
        return new Rectangle((int) x, (int) y, w, h);
    }

    public int[][] getGrid() {
        return grid;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return grid[0].length * blockWidth;
    }

    public int getHeight() {
        return grid.length * blockHeight;
    }

    public int getGridHeight() {
        return grid.length;
    }

    public int getGridWidth() {
        return grid[0].length;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public int getBlockValue(int i, int j) {
        return grid[i][j];
    }
}
