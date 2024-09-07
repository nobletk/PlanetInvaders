package game;

import entities.UFO;
import entities.enemy.EnemyA;
import entities.enemy.EnemyB;
import entities.enemy.EnemyC;
import utils.FontLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static utils.GraphicsUtils.centerInvadersAndPts;
import static utils.GraphicsUtils.drawCenteredText;

public class GameMenu {
    private final String planetText, invadersText, playText, ten, twenty, forty, mystery;
    private final Font planetFont, invadersFont, ptsFont, playFont;
    private final Color blackColor, whiteColor, greenColor, tintColor;
    private final EnemyA enemyA;
    private final EnemyB enemyB;
    private final EnemyC enemyC;
    private final UFO ufo;
    private int blinkFreq, blinkTimer, numOfStars;
    private boolean visible;
    private ArrayList<Point> stars;

    public GameMenu() {
        blinkFreq = 120;
        visible = true;
        numOfStars = 200;

        enemyA = new EnemyA(0, 0, 0, 0, 0);
        enemyB = new EnemyB(0, 0, 0, 0, 0);
        enemyC = new EnemyC(0, 0, 0, 0, 0);
        ufo = new UFO(0, 0, 0);

        planetFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 100f);
        invadersFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 70f);
        ptsFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 40f);
        playFont = FontLoader.loadFont("/assets/fonts/ITCMachineMedium.otf", 40f);

        blackColor = GameColors.BACKGROUND.getColor();
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

        generateStars();
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(blackColor);
        g.fillRect(0, 0, GamePanel.getScreenWidth(), GamePanel.getScreenHeight());

        g.setColor(whiteColor);
        for (Point star : stars) {
            g.fillRect(star.x, star.y, 2, 2);
        }

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

    private void generateStars() {
        stars = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numOfStars; i++) {
            int x = random.nextInt(GamePanel.getScreenWidth());
            int y = random.nextInt(GamePanel.getScreenHeight());
            stars.add(new Point(x, y));
        }
    }
}
