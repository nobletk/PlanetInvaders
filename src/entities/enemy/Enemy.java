package entities.enemy;

import java.awt.*;

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

    void animate();

    void destroy();

    void explode(Graphics g);

    boolean isExploding();

    boolean isReadyForRemoval();

    boolean isDead();

    void setDead(boolean dead);

    float getVelX();

    void setVelX(float velX);

    float getIncVelX();

    void setIncVelX(float incVelX);

    void sideMovement();

    void moveDownward();

    int getPoints();
}
