package entities;

import java.awt.*;

public class Bullet extends Entity {
    private static final int width = 3;
    private static final int height = 8;
    private float velocity;

    public Bullet(float x, float y, float velocity) {
        super(x, y);
        this.velocity = velocity;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.RED);
        g2D.draw(getBounds(x, y, width, height));

        g.setColor(Color.CYAN);
        g.fillOval((int) x, (int) y, width, height);
    }

    @Override
    public void update() {
        y += velocity;
    }


    @Override
    public Rectangle getBounds(float x, float y, int w, int h) {
        int rectangleX = (int) x;
        int rectangleY = (int) y + (int) velocity;
        int rectangleWidth = width;
        int rectangleHeight = height + (int) velocity / 2;

        return new Rectangle(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
    }
}

