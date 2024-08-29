package game;

import entities.Player;
import fontLoader.FontLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScore {
    private final Color textColor, playerColor;
    private Font itcMachineFont;
    private List<Player> livesList = new ArrayList<>();
    private int playerScore;
    private int numOfLives;

    public GameScore(Game game) {
        this.playerScore = 0;
        this.numOfLives = 3;
        this.itcMachineFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 26f);
        this.textColor = GameColors.TEXT.getColor();
        this.playerColor = GameColors.PLAYER.getColor();
        initLives();
    }

    //screenWidth = 900, height = 1024
    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.setFont(itcMachineFont);
        g2D.setColor(textColor);
        g2D.drawString("SCORE<1>", 40, 40);
        g2D.drawString(getPaddedScore(), 40, 65);


        g.setColor(playerColor);
        for (int i = 0; i < numOfLives; i++) {
            Player p = livesList.get(i);
            p.render(g);
        }
        g2D.setColor(textColor);
        g2D.drawString(String.valueOf(numOfLives), 40, 987);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawLine(20, 950, 880, 950);
    }

    public void update() {
    }

    private void initLives() {
        for (int i = 0; i < numOfLives; i++) {
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

    public void addLife() {
        numOfLives++;
    }

    public int getNumOfLives() {
        return numOfLives;
    }

    private String getPaddedScore() {
        return String.format("%04d", playerScore);
    }
}
