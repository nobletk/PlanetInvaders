package game;

import entities.Player;
import entityManager.AmmoManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    public static boolean leftPressed, rightPressed, spacePressed, enterPressed, quitPressed, restartPressed;
    private Game game;
    private AmmoManager ammoManager;
    private Player player;

    public KeyInput(GamePanel gamePanel) {
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
            case KeyEvent.VK_LEFT:
                if (GameState.state == GameState.RUNNING) {
                    leftPressed = true;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (GameState.state == GameState.RUNNING) {
                    rightPressed = true;
                }
                break;
            case KeyEvent.VK_SPACE:
                if (GameState.state == GameState.RUNNING && onePlayerBullet()) {
                    spacePressed = true;
                    ammoManager.addPlayerBullet(player.getX() + (float) player.getWidth() / 2, player.getY() - 20);
                }
                break;
            case KeyEvent.VK_ENTER:
                if (GameState.state == GameState.MENU) {
                    enterPressed = true;
//                    System.out.println("enter pressed");
                    GameState.state = GameState.RUNNING;
                }
                break;
            case KeyEvent.VK_R:
                if (GameState.state == GameState.GAME_OVER) {
                    restartPressed = true;
//                    System.out.println("restarting");
                    break;
                }
                break;
            case KeyEvent.VK_Q:
                if (GameState.state == GameState.GAME_OVER) {
                    quitPressed = true;
//                    System.out.println("q pressed");
                    System.exit(0);
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false;
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = false;
                break;
            case KeyEvent.VK_Q:
                quitPressed = false;
                break;
            case KeyEvent.VK_R:
                restartPressed = false;
                break;
        }
    }

    public void refreshInstances() {
        this.player = game.getPlayer();
        this.ammoManager = game.getAmmoManager();
    }

    private boolean onePlayerBullet() {
        return ammoManager.getPlayerBulletsListSize() == 0;
    }
}
