package org.mangorage.virusgame;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.level.tile.Tile;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.render.RenderManager;
import org.mangorage.virusgame.render.RenderStack;
import org.mangorage.virusgame.vector.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class RenderScreen extends JPanel {

    private final RenderStack stack = RenderStack.of();
    private final Level level = Game.getInstance().getLevel();

    public RenderScreen() {
        Timer renderer = new Timer((1 / 60), a -> repaint());
        renderer.start();

        stack
                .addRenderer(g2 -> {
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                })
                .push("game")
                .addRenderer(g2 -> {
                    level.forEach((p, t) -> {
                        if (t.getValue().canRender()) {
                            RenderManager.getTileRenderer().getRenderer(t.getId())
                                    .render(g2, level, p, t);
                        }
                        return false;
                    });
                });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        stack.render(g);
    }
}
