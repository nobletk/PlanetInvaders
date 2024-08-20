package game;

import entities.Player;
import fontLoader.FontLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScore {
    private Font itcMachineFont;
    private List<Player> livesList = new ArrayList<>();
    private int playerScore;
    private int numOfLives;

    public GameScore(Game game) {
        this.playerScore = 0;
        this.numOfLives = 2;
        this.itcMachineFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 26f);
        initLives();
    }

    //screenWidth = 900, height = 1024
    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.setFont(itcMachineFont);
        g2D.setColor(Color.cyan);
        g2D.drawString("SCORE<1>", 40, 40);
        g2D.drawString(getPaddedScore(), 40, 65);


        g.setColor(Color.CYAN);
        for (int i = 0; i < numOfLives; i++) {
            Player p = livesList.get(i);
            p.render(g);
        }
        g2D.setColor(Color.CYAN);
        g2D.drawString(String.valueOf(numOfLives + 1), 40, 987);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawLine(20, 950, 880, 950);
    }

    public void update() {
    }

    private void initLives() {
        for (int i = 0; i < 2; i++) {
            Player p = new Player(0, 0);
            float x = 70 + i * (p.getWidth() + 15);
            float y = 960;
            livesList.add(new Player(x, y));
        }
    }

    public void addPoints(int pts) {
        this.playerScore += pts;
    }

    public void removeLife() {
        numOfLives--;
    }

    private String getPaddedScore() {
        return String.format("%04d", playerScore);
    }
}
