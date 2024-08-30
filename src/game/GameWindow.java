package game;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel gamePanel) {
        this.setTitle("Planet Invaders");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(gamePanel);
//        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
}
