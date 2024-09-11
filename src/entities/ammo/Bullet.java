package entities.ammo;

import entities.Entity;
import game.GameColors;
import utils.SoundPlayer;

import java.awt.*;

public class Bullet extends Entity implements Ammo {
    private final Color color;
    private float velY;
    private SoundPlayer sound;

    public Bullet(float x, float y, float velY) {
        super(x, y);
        this.velY = velY;
        this.color = GameColors.PLAYER.getColor();

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
                g.setColor(color);
                g.fillOval((int) xFill, (int) yFill, getBlockWidth(), getBlockHeight());
            }
        }
    }

    @Override
    public void update() {
        y += velY;
    }


    @Override
    public Rectangle getBounds(float x, float y, int width, int height) {
        int rectangleX = (int) x;
        int rectangleY = (int) (y + velY);
        int rectangleHeight = height + (int) Math.round(velY / 2.0);

        return new Rectangle(rectangleX, rectangleY, width, rectangleHeight);
    }
}

