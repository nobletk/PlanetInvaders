package entities.ammo;

import java.awt.*;

public interface Ammo {

    float getX();

    float getY();

    int getBlockWidth();

    int getBlockHeight();

    void render(Graphics g);

    void update();

    Rectangle getBounds(float x, float y, int width, int height);
}
