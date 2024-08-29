package entities;

import game.GameColors;
import game.GamePanel;
import game.KeyInput;
import game.SoundPlayer;

import java.awt.*;

public class Player extends Entity {
    private final Color color;
    private float playerSpeed, respawnX, respawnY;
    private int blinkFreq, respawnDuration, respawnTimer;
    private boolean respawning, visible;

    public Player(float x, float y) {
        super(x, y);
        this.respawnX = x;
        this.respawnY = y;
        this.playerSpeed = 1.0f;
        this.blinkFreq = 10;
        this.respawning = false;
        this.visible = true;
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
                    if (visible) {
                        g.setColor(color);
                        g.fillRect((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        if (!respawning) move();
        updateRespawn();
    }

    public void respawn() {
        this.x = respawnX;
        this.y = respawnY;
        this.respawnDuration = 120;
        this.respawnTimer = 0;
        this.respawning = true;
        this.visible = true;
    }

    private void updateRespawn() {
        if (respawning) {
            respawnTimer++;
            if (respawnTimer % blinkFreq == 0) visible = !visible;
            if (respawnTimer >= respawnDuration) {
                respawning = false;
                visible = true;
            }
        }
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

    public void playExplosion() {
        SoundPlayer sound = new SoundPlayer("src/assets/sound/playerExplosion.wav");
        sound.setVolume(-10.0f);
        sound.play();
    }

    public boolean isRespawning() {
        return respawning;
    }
}
