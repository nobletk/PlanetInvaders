package game;

import entities.Entity;
import entities.UFO;
import entities.enemy.EnemyA;
import entities.enemy.EnemyB;
import entities.enemy.EnemyC;
import fontLoader.FontLoader;

import java.awt.*;

public class GameMenu {
    private final String planetText, invadersText, playText, ten, twenty, forty, mystery;
    private final Font planetFont, invadersFont, ptsFont, playFont;
    private final Color whiteColor, greenColor, tintColor;
    private final EnemyA enemyA;
    private final EnemyB enemyB;
    private final EnemyC enemyC;
    private final UFO ufo;
    private int blinkFreq, blinkTimer;
    private boolean visible;

    public GameMenu() {
        blinkFreq = 120;
        visible = true;

        enemyA = new EnemyA(0, 0, 0, 0, 0);
        enemyB = new EnemyB(0, 0, 0, 0, 0);
        enemyC = new EnemyC(0, 0, 0, 0, 0);
        ufo = new UFO(0, 0, 0);

        planetFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 100f);
        invadersFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 70f);
        ptsFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 40f);
        playFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 40f);

        whiteColor = GameColors.TEXT.getColor();
        greenColor = GameColors.PLAYER.getColor();
        tintColor = GameColors.ENEMY.getColor();

        planetText = "planet";
        invadersText = "invaders";
        ten = "= 10 pts";
        twenty = "= 20 pts";
        forty = "= 40 pts";
        mystery = "= ??? pts";
        playText = "press enter to play";
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.getScreenWidth(), GamePanel.getScreenHeight());

        g2d.setFont(planetFont);
        g2d.setColor(whiteColor);
        drawCenteredText(g2d, planetText, 200);

        g2d.setFont(invadersFont);
        g2d.setColor(greenColor);
        drawCenteredText(g2d, invadersText, 252);

        g2d.setFont(ptsFont);
        g2d.setColor(tintColor);
        centerInvadersAndPts(g, enemyC, ten, 350);
        centerInvadersAndPts(g, enemyB, twenty, 420);
        centerInvadersAndPts(g, enemyA, forty, 490);
        centerInvadersAndPts(g, ufo, mystery, 560);

        g2d.setFont(playFont);
        g2d.setColor(whiteColor);
        if (visible) drawCenteredText(g2d, playText, 800);
    }

    public void update() {
        playTextBlink();
    }

    private void playTextBlink() {
        blinkTimer++;
        if (blinkTimer >= blinkFreq) {
            visible = !visible;
            blinkTimer = 0;
        }
    }

    private void drawCenteredText(Graphics2D g2d, String text, int y) {
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int textWidth = metrics.stringWidth(text);
        int x = (GamePanel.getScreenWidth() - textWidth) / 2;
        g2d.drawString(text, x, y);
    }

    private void centerInvadersAndPts(Graphics g, Entity entity, String ptsText, int y) {
        Graphics2D g2d = (Graphics2D) g;
        int entityWidth = entity.getWidth();
        int entityHeight = entity.getHeight();
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int textWidth = metrics.stringWidth(ptsText);
        int totalWidth = entityWidth + textWidth + 10;
        int xStart = (GamePanel.getScreenWidth() - totalWidth) / 2;

        entity.setPosition(xStart, y);
        entity.render(g);
        int textY = y + (entityHeight / 2) + (metrics.getAscent() / 2) - 2;
        g2d.drawString(ptsText, xStart + entityWidth + 10, textY);
    }
}
