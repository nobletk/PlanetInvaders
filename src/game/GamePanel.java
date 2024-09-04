package game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int screenWidth = 900;
    private static final int screenHeight = 1024;
    private Game game;
    private KeyInput keyInput;

    public GamePanel(Game game) {
        this.game = game;
        keyInput = new KeyInput(this);
        this.addKeyListener(keyInput);
        this.setFocusable(true);
        this.requestFocusInWindow();

        setPanelSize();
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(screenWidth, screenHeight);
        setPreferredSize(size);
    }

    public Game getGame() {
        return game;
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }
}
