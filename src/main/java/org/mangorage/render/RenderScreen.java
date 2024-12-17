package org.mangorage.render;

import org.mangorage.render.core.Level;
import org.mangorage.render.core.RenderStack;
import org.mangorage.render.core.vector.Vector2D;
import org.mangorage.render.core.game.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RenderScreen extends JPanel {

    private final RenderStack stack = RenderStack.of();
    private final Level level;
    private final Random random = new Random();

    public RenderScreen(Level level, int scale) {
        this.level = level;

        Timer renderer = new Timer((1 / 60), a -> repaint());
        renderer.start();

        Timer ticker = new Timer(1, a -> level.tick());
        ticker.setInitialDelay(250);
        ticker.start();

        stack
                .addRenderer(g2 -> {
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                })
                .push("game")
                .addRenderer(g2 -> {
                    level.forEach((pos, tile) -> {
                        if (tile.getValue().canRender()) {
                            g2.setColor(tile.getValue().getColor());
                            g2.fillRect(
                                    pos.x() * scale,
                                    pos.y() * scale,
                                    scale,
                                    scale
                            );
                        }
                        return false;
                    });
                });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, 300, 300);
        stack.render(g);
    }

    public Level getLevel() {
        return level;
    }
}
