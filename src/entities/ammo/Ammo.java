package entities.ammo;

import java.awt.*;

public interface Ammo {

    float getX();

    float getY();

    int getGridHeight();

    int getGridWidth();

    int getWidth();

    int getHeight();

    int[][] getGrid();

    int getBlockWidth();

    int getBlockHeight();

    int getBlockValue(int i, int j);

    void render(Graphics g);

    void update();

    Rectangle getBounds(float xBlock, float yBlock, int blockSize, int blockSize1);
}
