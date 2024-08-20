package entities;

import game.KeyInput;
import game.GamePanel;

import java.awt.*;

public class Player extends Entity {
    private static float playerSpeed = 1;

    public Player(float x, float y) {
        super(x, y);
        this.gridWidth = 13;
        this.gridHeight = 8;
        this.blockSize = 3;

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

        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if (grid[i][j] == 1) {
                    float xFill = x + j * blockSize;
                    float yFill = y + i * blockSize;
                    g.setColor(Color.CYAN);
                    g.fillRect((int) xFill, (int) yFill, blockSize, blockSize);
                    g2D.setColor(Color.darkGray);
                    g2D.draw(getBounds(xFill, yFill, blockSize, blockSize));
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
