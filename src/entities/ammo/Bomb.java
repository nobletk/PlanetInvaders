package entities.ammo;

import entities.Entity;
import game.GameColors;

import java.awt.*;

public class Bomb extends Entity implements Ammo {
    private final Color color;
    private float velY;

    public Bomb(float x, float y, float velY) {
        super(x, y);
        this.velY = velY;
        this.color = GameColors.ENEMY.getColor();

        this.grid = new int[][]{
                {0, 0, 1},
                {0, 1, 0},
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1},
                {0, 1, 0},
                {1, 0, 0},
        };
    }

    @Override
    public void render(Graphics g) {
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

    @Override
    public void update() {
        y += velY;
    }

    @Override
    public Rectangle getBounds(float x, float y, int w, int h) {
        int rectangleX = (int) x;
        int rectangleY = (int) y + (int) velY;
        int rectangleWidth = getWidth();
        int rectangleHeight = getHeight() + (int) velY / 2;

        return new Rectangle(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
    }
}
