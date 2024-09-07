package entityManager;

import entities.Player;
import entities.ammo.Bomb;
import entities.ammo.Bullet;
import game.GamePanel;
import game.GameScore;
import levels.LevelManager;
import physics.Collision;

import java.awt.*;
import java.util.LinkedList;

public class AmmoManager {
    private final float bulletVel, bombVel;
    private final Player player;
    private final GameScore score;
    private final Collision collision;
    private final LinkedList<Bullet> playerBullets = new LinkedList<>();
    private final LinkedList<Bomb> enemyBullets = new LinkedList<>();

    public AmmoManager(LevelManager levelManager) {
        this.player = levelManager.getPlayer();
        this.score = levelManager.getScore();
        this.collision = new Collision(levelManager, this);
        this.bulletVel = -3.5f;
        this.bombVel = 1.5f;
    }

    public void render(Graphics g) {
        for (int i = 0; i < playerBullets.size(); i++) {
            Bullet b = playerBullets.get(i);
            b.render(g);
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bomb b = enemyBullets.get(i);
            b.render(g);
        }
    }

    public void update() {
        updatePlayerBullets();
        updateEnemyBullets();
    }

    private void updatePlayerBullets() {
        for (int i = 0; i < playerBullets.size(); i++) {
            Bullet b = playerBullets.get(i);
            if (b.getY() < 0) {
                removePlayerBullet(b);
            }
            if (collision.checkEnemyCollision(b)) {
                removePlayerBullet(b);
            }
            if (collision.checkBunkerCollision(b)) {
                removePlayerBullet(b);
            }
            if (collision.checkUFOCollision(b)) {
                removePlayerBullet(b);
            }
            if (collision.checkEnemyBombCollision(b)) {
                removePlayerBullet(b);
            }
            b.update();
        }
    }

    private void updateEnemyBullets() {
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bomb b = enemyBullets.get(i);
            if (b.getY() > GamePanel.getScreenHeight()) {
                removeEnemyBomb(b);
            }
            if (collision.checkBunkerCollision(b)) {
                removeEnemyBomb(b);
            }
            if (collision.checkPlayerCollision(b) && !player.isRespawning()) {
                if (score.getNumOfLives() > 0) {
                    player.playExplosion();
                    score.removeLife();
                    player.respawn();
                    removeEnemyBomb(b);
                }
            }
            b.update();
        }
    }

    public void addPlayerBullet(float x, float y) {
        playerBullets.add(new Bullet(x, y, bulletVel));
    }

    public void removePlayerBullet(Bullet b) {
        playerBullets.remove(b);
    }

    public void addEnemyBullet(float x, float y) {
        enemyBullets.add(new Bomb(x, y, bombVel));
    }

    public void removeEnemyBomb(Bomb b) {
        enemyBullets.remove(b);
    }

    public int getPlayerBulletsListSize() {
        return playerBullets.size();
    }

    public LinkedList<Bomb> getEnemyBombs() {
        return enemyBullets;
    }
}
