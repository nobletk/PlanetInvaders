package entities.enemy;

import java.awt.*;

public interface Enemy {

    float getX();

    float getY();

    int getWidth();

    int getHeight();

    int getBlockWidth();

    int getBlockHeight();

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
