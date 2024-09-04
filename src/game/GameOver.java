package game;

import fontLoader.FontLoader;

import java.awt.*;

public class GameOver {
    private final String gameOverText, promptText;
    private final Color white;
    private final Font gameOverFont, promptFont;
    private int blinkFreq, blinkTimer;
    private boolean visible;

    public GameOver() {
        blinkFreq = 120;
        visible = true;

        gameOverFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 70f);
        promptFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 40f);

        white = GameColors.TEXT.getColor();
        gameOverText = "game over";
        promptText = "press R to restart or Q to exit";
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(white);
        g2d.setFont(gameOverFont);
        drawCenteredText(g2d, gameOverText, 450);

        g2d.setFont(promptFont);
        if (visible) drawCenteredText(g2d, promptText, 550);
    }

    public void update() {
        promptTextBlink();
    }

    private void drawCenteredText(Graphics2D g2d, String text, int y) {
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int textWidth = metrics.stringWidth(text);
        int x = (GamePanel.getScreenWidth() - textWidth) / 2;
        g2d.drawString(text, x, y);
    }

    private void promptTextBlink() {
        blinkTimer++;
        if (blinkTimer >= blinkFreq) {
            visible = !visible;
            blinkTimer = 0;
        }
    }
}
