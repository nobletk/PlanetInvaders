package physics;

import entities.Bunker;
import entities.Entity;
import entities.Player;
import entities.ammo.Ammo;
import entities.ammo.Bomb;
import entities.ammo.Bullet;
import entities.enemy.Enemy;
import entities.enemy.EnemyD;
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

    public boolean checkBunkerCollision(Ammo ammo) {
        List<Bunker> bunkers = bunkerManager.getBunkers();
        for (int i = 0; i < bunkers.size(); i++) {
            Bunker bun = bunkers.get(i);
            if (checkCollisionWithEntity(ammo, bun)) {
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
                    e.setDead(true);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkUFOCollision(Bullet b) {
        EnemyD ufo = enemyManager.getUFO();
        if (ufo != null && checkCollisionWithEntity(b, ufo)) {
            ufo.setDead(true);
            return true;
        }
        return false;
    }

    public boolean checkPlayerCollision(Bomb b) {
        if (checkCollisionWithEntity(b, player)) {
            System.out.println("Collided with player");
            return true;
        }
        return false;
    }

    private boolean checkCollisionWithEntity(Ammo a, Entity e) {
        for (int row = 0; row < e.getGridHeight(); row++) {
            for (int col = 0; col < e.getGridWidth(); col++) {
                if (isBlockCollidable(e, row, col) && isAmmoCollidingWithBlock(a, e, row, col)) {
                    if (e instanceof Bunker) {
                        ((Bunker) e).destroyBlocks(row, col);
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

    private boolean isAmmoCollidingWithBlock(Ammo a, Entity e, int row, int col) {
        float x = e.getX() + col * e.getBlockWidth();
        float y = e.getY() + row * e.getBlockHeight();
        Rectangle entityBounds = e.getBounds(x, y, e.getBlockWidth(), e.getBlockHeight());
        Rectangle ammoBounds = a.getBounds(a.getX(), a.getY(), a.getBlockWidth(), a.getBlockHeight());
        return entityBounds.intersects(ammoBounds);
    }

}
