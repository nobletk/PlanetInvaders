package entityManager;

import entities.Bunker;
import game.Game;
import game.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BunkerManager {
    private List<Bunker> bunkers = new ArrayList<>();
    private Game game;
    private float x, y;

    public BunkerManager(float x, float y, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
        initBunkers();
    }

    public void render(Graphics g) {
        for (int i = 0; i < bunkers.size(); i++) {
            Bunker b = bunkers.get(i);
            b.render(g);
        }
    }

    public void update() {
        for (int i = 0; i < bunkers.size(); i++) {
            Bunker b = bunkers.get(i);
            b.update();
        }
    }

    private void initBunkers() {
        int numOfBunkers = 5;
        Bunker bunker = new Bunker(0, 0);
        int gap = (GamePanel.getScreenWidth() - (numOfBunkers * bunker.getWidth())) / (numOfBunkers + 1);

        for (int i = 0; i < numOfBunkers; i++) {
            float xPos = x + i * (bunker.getWidth() + gap);
            bunkers.add(new Bunker(xPos, y));
        }
    }

    public List<Bunker> getBunkers() {
        return bunkers;
    }
}
