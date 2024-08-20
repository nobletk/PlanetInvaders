package entityManager;

import entities.enemy.Enemy;
import game.Game;

public class TaskManager {
    private Game game;
    private BulletManager bulletManager;
    private Enemy[][] enemies;
    private int enemyShotCooldown = 0;

    public TaskManager(Game game) {
        this.game = game;
        this.bulletManager = game.getBulletManager();
        this.enemies = game.getEnemyManager().getEnemies();
    }

    public void update() {
        if (enemyShotCooldown > 0) enemyShotCooldown--;
        enemyShoots();
    }

    private void enemyShoots() {
        if (enemyShotCooldown == 0) {
            int row = (int) (Math.random() * EnemyManager.enemyRows);
            int col = (int) (Math.random() * EnemyManager.enemyCols);

            Enemy e = enemies[row][col];
            if (e == null || e.isDead()) return;

            if (row == EnemyManager.enemyRows - 1 || enemies[row + 1][col] == null) {
                bulletManager.addEnemyBullet(e.getX() + ((float) e.getWidth() / 2), e.getY() + e.getHeight());
                enemyShotCooldown = 500;
                System.out.printf("shot %d %d\n", row, col);
            }
        }
    }
}
