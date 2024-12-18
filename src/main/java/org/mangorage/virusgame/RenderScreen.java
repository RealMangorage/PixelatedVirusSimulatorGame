package org.mangorage.virusgame;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.misc.SharedConstants;
import org.mangorage.virusgame.render.RenderStack;

import javax.swing.*;
import java.awt.*;
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
                    level.forEach((pos, tile) -> {
                        if (tile.getValue().canRender()) {
                            g2.setColor(tile.getValue().getColor());
                            g2.fillRect(
                                    pos.x() * SharedConstants.scale,
                                    pos.y() * SharedConstants.scale,
                                    SharedConstants.scale,
                                    SharedConstants.scale
                            );
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