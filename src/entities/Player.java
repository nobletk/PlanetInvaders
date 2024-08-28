package entities;

import game.GameColors;
import game.GamePanel;
import game.KeyInput;

import java.awt.*;

public class Player extends Entity {
    private static float playerSpeed = 1;
    private final Color color;

    public Player(float x, float y) {
        super(x, y);
        this.color = GameColors.PLAYER.getColor();

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
