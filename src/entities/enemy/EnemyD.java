package entities.enemy;

import entities.Entity;
import game.SoundPlayer;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EnemyD extends Entity implements Enemy {
    private float velX;
    private int pts;
    private boolean dead;
    private SoundPlayer sound;

    public EnemyD(float x, float y, float velX) {
        super(x, y);
        this.velX = velX;
        this.dead = false;
        this.pts = mysteryPts();

        sound = new SoundPlayer("src/assets/sound/ufo.wav");
        sound.setLoop(true);
        sound.setDelay(300);
        sound.setVolume(-10.f);
        sound.play();

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
                    g.setColor(Color.RED);
                    g.fillRect((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
                }
            }
        }
    }

    @Override
    public void update() {
        sideMovement();
        if (getX() <= 0 || isDead()) {
            stopSound();
        }
    }

    @Override
    public void sideMovement() {
        x += velX;
    }

    @Override
    public float getVelX() {
        return velX;
    }

    @Override
    public void setVelX(float velX) {
        this.velX = velX;
    }

    @Override
    public float getIncVelX() {
        return 0;
    }

    @Override
    public void setIncVelX(float incVelX) {
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public int getPoints() {
        return pts;
    }

    @Override
    public void moveDownward() {
    }

    public void stopSound() {
        if (sound != null) {
            System.out.println("stopping sound");
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
