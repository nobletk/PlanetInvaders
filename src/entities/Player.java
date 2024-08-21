package entities;

import game.GamePanel;
import game.KeyInput;

import java.awt.*;

public class Player extends Entity {
    private static float playerSpeed = 1;

    public Player(float x, float y) {
        super(x, y);

        this.grid = new int[][]{
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
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
                    g.setColor(Color.CYAN);
                    g.fillRect((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
                    g2D.setColor(Color.darkGray);
                    g2D.draw(getBounds(xFill, yFill, getBlockWidth(), getBlockHeight()));
                }
            }
        }
    }

    @Override
    public void update() {
        move();
    }

    private void move() {
        if (KeyInput.leftPressed) {
            x -= playerSpeed;
            if (x <= 0) {
                x = 0;
            }
        }
        if (KeyInput.rightPressed) {
            x += playerSpeed;
            if (x >= GamePanel.getScreenWidth() - getWidth()) {
                x = GamePanel.getScreenWidth() - getWidth();
            }
        }
    }
}
