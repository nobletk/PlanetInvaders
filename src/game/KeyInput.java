package game;

import entities.Player;
import entityManager.AmmoManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    public static boolean leftPressed, rightPressed, spacePressed;
    private GamePanel gamePanel;
    private Game game;
    private AmmoManager ammoManager;
    private Player player;

    public KeyInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.game = gamePanel.getGame();
        this.player = game.getPlayer();
        this.ammoManager = game.getAmmoManager();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_X:
                leftPressed = true;
                break;
            case KeyEvent.VK_V:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true;
                ammoManager.addPlayerBullet(player.getX() + (float) player.getWidth() / 2, player.getY() - 20);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_X:
                leftPressed = false;
                break;
            case KeyEvent.VK_V:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false;
                break;
        }
    }
}
