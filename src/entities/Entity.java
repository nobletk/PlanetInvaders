package entities;

import java.awt.*;

public abstract class Entity {

    protected int[][] grid;
    protected float x, y;
    protected int gridWidth, gridHeight, blockSize;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
        this.grid = new int[][]{};
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
        return gridWidth * blockSize;
    }

    public int getHeight() {
        return gridHeight * blockSize;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getBlockValue(int i, int j) {
        return grid[i][j];
    }
}
