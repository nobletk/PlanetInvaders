package entityManager;

import entities.enemy.Enemy;
import levels.LevelManager;

public class TaskManager {
    private final AmmoManager ammoManager;
    private final Enemy[][] enemies;
    private int enemyShotCooldown = 0;

    public TaskManager(LevelManager levelManager) {
        this.ammoManager = levelManager.getAmmoManager();
        this.enemies = levelManager.getEnemyManager().getEnemies();
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
                ammoManager.addEnemyBullet(e.getX() + ((float) e.getWidth() / 2), e.getY() + e.getHeight());
                enemyShotCooldown = 360;
//                System.out.printf("shot %d %d\n", row, col);
            }
        }
    }
}
