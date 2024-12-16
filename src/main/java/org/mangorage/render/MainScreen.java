package org.mangorage.render;

import org.mangorage.render.core.Level;
import org.mangorage.render.core.vector.Vector2D;

import javax.swing.*;

public class MainScreen {
    public static void main(String[] args) {

        Registries.bootstrap();

        JFrame window = new JFrame("Pixelated Virus Simulator!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(810, 634);
        window.setResizable(true);

        RenderScreen screen = new RenderScreen(
                Level.create(800, 600, 10),
                10
        );

        screen.getLevel().setTile(Vector2D.of(0, 0), Registries.Tiles.INFECTED);
        screen.getLevel().setTile(Vector2D.of(12, 1), Registries.Tiles.HEALTHY);

        window.setContentPane(screen);
        window.setVisible(true);
    }
}
