package entityManager;

import entities.ammo.Bomb;
import entities.ammo.Bullet;
import entities.Player;
import game.Game;
import game.GamePanel;
import game.GameScore;
import physics.Collision;

import java.awt.*;
import java.util.LinkedList;

public class AmmoManager {
    private Game game;
    private Player player;
    private EnemyManager enemyManager;
    private GameScore score;
    private Collision collision;

    private LinkedList<Bullet> playerBullets = new LinkedList<>();
    private LinkedList<Bomb> enemyBullets = new LinkedList<>();

    public AmmoManager(Game game) {
        this.game = game;
        this.player = game.getPlayer();
        this.enemyManager = game.getEnemyManager();
        this.score = game.getScore();
        this.collision = new Collision(game);
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
            b.update();
        }
    }

    private void updateEnemyBullets() {
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bomb b = enemyBullets.get(i);
            if (b.getY() > GamePanel.getScreenHeight()) {
                removeEnemyBullet(b);
            }
            if (collision.checkBunkerCollision(b)) {
                removeEnemyBullet(b);
            }
            if (collision.checkPlayerCollision(b)) {
                //TODO: reanimate the player on using a life
                score.removeLife();
                removeEnemyBullet(b);
            }
            b.update();
        }
    }

    public void addPlayerBullet(float x, float y) {
        playerBullets.add(new Bullet(x, y, -2));
    }

    public void removePlayerBullet(Bullet b) {
        playerBullets.remove(b);
    }

    public void addEnemyBullet(float x, float y) {
        enemyBullets.add(new Bomb(x, y, 1));
    }

    public void removeEnemyBullet(Bomb b) {
        enemyBullets.remove(b);
    }
}
