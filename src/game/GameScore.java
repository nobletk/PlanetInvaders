package game;

import entities.Player;
import utils.FontLoader;
import utils.SoundPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScore {
    private final Color textColor, playerColor;
    private final Font itcMachineFont;
    private final List<Player> livesList = new ArrayList<>();
    private final int maxLives;
    private final int extraLifeThreshold;
    private int playerScore, hiScore, playerBulletCount;
    private int numOfLives;
    private boolean extraLifeGained;

    public GameScore() {
        this.playerBulletCount = 0;
        this.playerScore = 0;
        this.numOfLives = 3;
        this.maxLives = 4;
        this.extraLifeThreshold = 1000;
        this.extraLifeGained = false;
        this.itcMachineFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 30f);
        this.textColor = GameColors.TEXT.getColor();
        this.playerColor = GameColors.PLAYER.getColor();
        initLives();
    }

    //screenWidth = 900, height = 1024
    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        String scoreText = "score <1>";
        String hiScoreText = "hi-score";

        g2D.setFont(itcMachineFont);
        g2D.setColor(textColor);
        g2D.drawString(scoreText, 40, 35);
        g2D.drawString(hiScoreText, 400, 35);
        g2D.drawString(getPaddedScore(playerScore), 60, 65);
        g2D.drawString(getPaddedScore(hiScore), 420, 65);


        g.setColor(playerColor);
        for (int i = 0; i < numOfLives - 1; i++) {
            Player p = livesList.get(i);
            p.render(g);
        }
        g2D.setColor(textColor);
        g2D.drawString(String.valueOf(numOfLives), 40, 987);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawLine(20, 950, 880, 950);
    }

    public void update() {
        extraLifeCheck();
    }

    public void updateHighScore() {
        if (playerScore > hiScore) hiScore = playerScore;
    }

    private void initLives() {
        for (int i = 0; i < maxLives; i++) {
            Player p = new Player(0, 0);
            float x = 70 + i * (p.getWidth() + 15);
            float y = 960;
            livesList.add(new Player(x, y));
        }
    }

    public void addPoints(int pts) {
        this.playerScore += pts;
    }

    public void incrementPlayerBulletCount() {
        playerBulletCount++;
    }

    public int getPlayerBulletCount() {
        return playerBulletCount;
    }

    public void removeLife() {
        numOfLives--;
        if (numOfLives == 0) {
            GameState.state = GameState.GAME_OVER;
        }
    }

    public void extraLifeCheck() {
        if (playerScore >= extraLifeThreshold && numOfLives < maxLives && !extraLifeGained) {
            SoundPlayer sound = new SoundPlayer("src/assets/sound/newLife.wav");
            sound.setVolume(-10.0f);
            sound.play();
            numOfLives += 1;
            extraLifeGained = true;
        }
    }

    public int getNumOfLives() {
        return numOfLives;
    }

    public void resetScore() {
        playerBulletCount = 0;
        playerScore = 0;
        numOfLives = 3;
        extraLifeGained = false;
    }

    private String getPaddedScore(int score) {
        return String.format("%04d", score);
    }
}
