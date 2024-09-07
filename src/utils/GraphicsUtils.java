package utils;

import entities.Entity;
import game.GamePanel;

import java.awt.*;

public class GraphicsUtils {

    public static void drawCenteredText(Graphics2D g2d, String text, int y) {
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int textWidth = metrics.stringWidth(text);
        int x = (GamePanel.getScreenWidth() - textWidth) / 2;
        g2d.drawString(text, x, y);
    }

    public static void centerInvadersAndPts(Graphics g, Entity entity, String ptsText, int y) {
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
