package entities;

import game.GameColors;
import game.SoundPlayer;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UFO extends Entity {
    private final Color color;
    private final int pts;
    private float velX;
    private boolean dead;
    private SoundPlayer sound;

    public UFO(float x, float y, float velX) {
        super(x, y);
        this.velX = velX;
        this.color = GameColors.UFO.getColor();
        this.dead = false;
        this.pts = mysteryPts();


        this.grid = new int[][]{
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
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
        sideMovement();
    }

    public void sideMovement() {
        x += velX;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getPoints() {
        return pts;
    }

    public void playSound() {
        sound = new SoundPlayer("src/assets/sound/ufo.wav");
        sound.setLoop(true);
        sound.setDelay(300);
        sound.setVolume(-10.f);
        sound.play();
    }

    public void stopSound() {
        if (sound != null) {
            sound.stop();
            sound = null;
        }
    }

    private int mysteryPts() {
        List<Integer> ptsList = Arrays.asList(50, 100, 150, 200, 300);
        Random rand = new Random();
        return ptsList.get(rand.nextInt(ptsList.size()));
    }
}
