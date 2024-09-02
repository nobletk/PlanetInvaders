package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameBackground {
    private final int numOfStars;
    private ArrayList<Point> stars;
    private ArrayList<Integer> starSizes;
    private Color[] starColors;
    private Random random;

    public GameBackground() {
        random = new Random();
        numOfStars = 200;
        generateStars();
        initializeStarColors();
    }

    public void render(Graphics g) {
        g.setColor(GameColors.BACKGROUND.getColor());
        g.fillRect(0, 0, GamePanel.getScreenWidth(), GamePanel.getScreenHeight());

        for (int i = 0; i < stars.size(); i++) {
            Point star = stars.get(i);
            int starSize = starSizes.get(i);
            g.setColor(starColors[random.nextInt(starColors.length)]);
            g.fillOval(star.x, star.y, starSize, starSize);
        }
        drawPlanet(g, -420, 700, 1800, 1200);
    }

    private void generateStars() {
        stars = new ArrayList<>();
        starSizes = new ArrayList<>();
        for (int i = 0; i < numOfStars; i++) {
            int x = random.nextInt(GamePanel.getScreenWidth());
            int y = random.nextInt(GamePanel.getScreenHeight());
            stars.add(new Point(x, y));
            starSizes.add(random.nextInt(3) + 1);
        }
    }

    private void initializeStarColors() {
        starColors = new Color[]{
                GameColors.TEXT.getColor(),
                GameColors.BLUEWHITE.getColor(),
                GameColors.REDWHITE.getColor(),
                GameColors.YELLOWWHITE.getColor()
        };
    }

    private void drawPlanet(Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        RadialGradientPaint paint = new RadialGradientPaint(
                new Point(x + width / 2, y + height / 2),
                (float) width / 2,
                new float[]{0.5f, 0.6f, 0.62f, 0.65f, 0.66f},
                new Color[]{
                        GameColors.PLANET1.getColor(),
                        GameColors.PLANET2.getColor(),
                        GameColors.PLANET3.getColor(),
                        GameColors.PLANET4.getColor(),
                        GameColors.PLANET5.getColor()
                }
        );
        g2d.setPaint(paint);
        g2d.fillOval(x, y, width, height);
    }
}
