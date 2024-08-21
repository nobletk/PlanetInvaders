package entities.enemy;

import java.awt.*;

//TODO: clean up enemy classes using this interface by implementing default methods
public interface Enemy {

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

    Rectangle getBounds(float xBlock, float yBlock, int blockSize, int blockSize1);

    void render(Graphics g);

    void update();

    default void destroy() {
        for (int i = 0; i < getGridHeight(); i++) {
            for (int j = 0; j < getGridWidth(); j++) {
                if (getGrid()[i][j] == 1) {
                    getGrid()[i][j] = 0;
                }
            }
        }
    }

    boolean isDead();

    void setDead(boolean b);

    float getVelX();

    void setVelX(float i);

    float getIncVelX();

    void setIncVelX(float i);

    void moveDownward();

    int getPoints();
}
