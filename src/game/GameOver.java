package game;

import utils.FontLoader;

import java.awt.*;

import static utils.GraphicsUtils.drawCenteredText;

public class GameOver {
    private final String gameOverText, promptText, wonText;
    private final Color white;
    private final Font gameOverFont, promptFont;
    private final int blinkFreq;
    private int blinkTimer;
    private boolean visible;

    public GameOver() {
        blinkFreq = 120;
        visible = true;

        gameOverFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 70f);
        promptFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 40f);

        white = GameColors.TEXT.getColor();
        gameOverText = "game over";
        wonText = "you won!";
        promptText = "press R to restart or Q to exit";
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(white);
        g2d.setFont(gameOverFont);

        if (GameState.state == GameState.GAME_OVER) drawCenteredText(g2d, gameOverText, 450);
        if (GameState.state == GameState.WON) drawCenteredText(g2d, wonText, 450);

        g2d.setFont(promptFont);
        if (visible) drawCenteredText(g2d, promptText, 550);
    }

    public void update() {
        promptTextBlink();
    }

    private void promptTextBlink() {
        blinkTimer++;
        if (blinkTimer >= blinkFreq) {
            visible = !visible;
            blinkTimer = 0;
        }
    }
}
