package entities.ammo;

import entities.Entity;
import game.SoundPlayer;

import java.awt.*;

public class Bullet extends Entity implements Ammo {
    private float velY;
    private SoundPlayer sound;

    public Bullet(float x, float y, float velY) {
        super(x, y);
        this.velY = velY;

        this.sound = new SoundPlayer("src/assets/sound/shoot.wav");
        sound.setVolume(-10.0f);
        sound.play();

        this.grid = new int[][]{
                {1},
                {1},
                {1},
                {1},
        };
    }

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                float xFill = x + j * getBlockWidth();
                float yFill = y + i * getBlockHeight();
                g.setColor(Color.CYAN);
                g.fillOval((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
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

