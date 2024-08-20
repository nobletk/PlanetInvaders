package physics;

import entities.Bullet;
import entities.Bunker;
import entities.Entity;
import entities.Player;
import entities.enemy.Enemy;
import entityManager.BunkerManager;
import entityManager.EnemyManager;
import game.Game;

import java.awt.*;
import java.util.List;

public class Collision {
    private Game game;
    private EnemyManager enemyManager;
    private BunkerManager bunkerManager;
    private Player player;

    public Collision(Game game) {
        this.game = game;
        this.enemyManager = game.getEnemyManager();
        this.bunkerManager = game.getBunkerManager();
        this.player = game.getPlayer();
    }

    public boolean checkBunkerCollision(Bullet bul) {
        List<Bunker> bunkers = bunkerManager.getBunkers();
        for (int i = 0; i < bunkers.size(); i++) {
            Bunker bun = bunkers.get(i);
            if (checkCollisionWithEntity(bul, bun)) {
                System.out.printf("Collided Bunker[%d]\n", i);
                return true;
            }
        }
        return false;
    }

    public boolean checkEnemyCollision(Bullet b) {
        Enemy[][] enemies = enemyManager.getEnemies();
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null && checkCollisionWithEntity(b, (Entity) e)) {
                    System.out.printf("Collided Entity[%d][%d]\n", i, j);
                    e.setDead(true);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkPlayerCollision(Bullet b) {
        if (checkCollisionWithEntity(b, player)) {
            System.out.println("Collided with player");
            return true;
        }
        return false;
    }

    private boolean checkCollisionWithEntity(Bullet b, Entity e) {
        for (int row = 0; row < e.getGridHeight(); row++) {
            for (int col = 0; col < e.getGridWidth(); col++) {
                if (isBlockCollidable(e, row, col) && isBulletCollidingWithBlock(b, e, row, col)) {
                    if (e instanceof Bunker) {
                        ((Bunker) e).updateBlock(row, col);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBlockCollidable(Entity e, int row, int col) {
        return e.getBlockValue(row, col) == 1;
    }

    private boolean isBulletCollidingWithBlock(Bullet b, Entity e, int row, int col) {
        float x = e.getX() + col * e.getBlockSize();
        float y = e.getY() + row * e.getBlockSize();
        Rectangle blockBounds = e.getBounds(x, y, e.getBlockSize(), e.getBlockSize());
        return blockBounds.intersects(b.getBounds(b.getX(), b.getY(), b.getBlockSize(), b.getBlockSize()));
    }

}
