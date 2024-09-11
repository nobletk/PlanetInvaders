package physics;

import entities.Bunker;
import entities.Entity;
import entities.Player;
import entities.UFO;
import entities.ammo.Ammo;
import entities.ammo.Bomb;
import entities.ammo.Bullet;
import entities.enemy.Enemy;
import entityManager.AmmoManager;
import entityManager.BunkerManager;
import entityManager.EnemyManager;
import levels.LevelManager;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Collision {
    private final EnemyManager enemyManager;
    private final BunkerManager bunkerManager;
    private final Player player;
    private final AmmoManager ammoManager;

    public Collision(LevelManager levelManager, AmmoManager ammoManager) {
        this.player = levelManager.getPlayer();
        this.bunkerManager = levelManager.getBunkerManager();
        this.enemyManager = levelManager.getEnemyManager();
        this.ammoManager = ammoManager;
    }

    public boolean checkEnemyBombCollision(Bullet bullet) {
        LinkedList<Bomb> enemyBombs = ammoManager.getEnemyBombs();
        for (int i = 0; i < enemyBombs.size(); i++) {
            Bomb bomb = enemyBombs.get(i);
            if (isAmmoColliding(bullet, bomb)) {
                ammoManager.removeEnemyBomb(bomb);
                return true;
            }
        }
        return false;
    }

    public boolean checkBunkerCollision(Ammo ammo) {
        List<Bunker> bunkers = bunkerManager.getBunkers();
        for (int i = 0; i < bunkers.size(); i++) {
            Bunker bunker = bunkers.get(i);
            if (checkCollisionWithEntity(ammo, bunker)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEnemyCollision(Bullet bullet) {
        Enemy[][] enemies = enemyManager.getEnemies();
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                Enemy e = enemies[i][j];
                if (e != null && checkCollisionWithEntity(bullet, (Entity) e)) {
                    e.setDead(true);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkUFOCollision(Bullet bomb) {
        UFO ufo = enemyManager.getUFO();
        if (ufo != null && checkCollisionWithEntity(bomb, ufo)) {
            ufo.setDead(true);
            return true;
        }
        return false;
    }

    public boolean checkPlayerCollision(Bomb bomb) {
        if (checkCollisionWithEntity(bomb, player)) {
            return true;
        }
        return false;
    }

    private boolean checkCollisionWithEntity(Ammo ammo, Entity entity) {
        for (int row = 0; row < entity.getGridHeight(); row++) {
            for (int col = 0; col < entity.getGridWidth(); col++) {
                if (isBlockCollidable(entity, row, col) && isAmmoCollidingWithBlock(ammo, entity, row, col)) {
                    checkBunkerDestroyBlocks(entity, ammo, row, col);
                    return true;
                }
            }
        }
        return false;
    }

    private void checkBunkerDestroyBlocks(Entity entity, Ammo ammo, int row, int col) {
        if (entity instanceof Bunker) {
            if (ammo instanceof Bullet)
                ((Bunker) entity).destroyBlocks(row, col, 1);
            if (ammo instanceof Bomb)
                ((Bunker) entity).destroyBlocks(row, col, 2);
        }
    }

    private boolean isBlockCollidable(Entity entity, int row, int col) {
        return entity.getBlockValue(row, col) == 1;
    }

    private boolean isAmmoCollidingWithBlock(Ammo ammo, Entity entity, int row, int col) {
        float x = entity.getX() + col * entity.getBlockWidth();
        float y = entity.getY() + row * entity.getBlockHeight();
        Rectangle entityBounds = entity.getBounds(x, y, entity.getBlockWidth(), entity.getBlockHeight());
        Rectangle ammoBounds = ammo.getBounds(ammo.getX(), ammo.getY(), ammo.getBlockWidth(), ammo.getBlockHeight());
        return entityBounds.intersects(ammoBounds);
    }

    private boolean isAmmoColliding(Bullet bullet, Bomb bomb) {
        Rectangle bulletBounds = bullet.getBounds(bullet.getX(), bullet.getY(), bullet.getBlockWidth(), bullet.getBlockHeight());
        Rectangle bombBounds = bomb.getBounds(bomb.getX(), bomb.getY(), bomb.getBlockWidth(), bomb.getBlockHeight());
        return bulletBounds.intersects(bombBounds);
    }
}
