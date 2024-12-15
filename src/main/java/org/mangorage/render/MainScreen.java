package org.mangorage.render;

import org.mangorage.render.core.Level;
import org.mangorage.render.core.vector.Vector2D;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainScreen {
    public static void main(String[] args) {

        Registries.bootstrap();

        JFrame window = new JFrame("Pixelated Virus Simulator!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(810, 634);
        window.setResizable(true);

        RenderScreen screen = new RenderScreen(Level.create(800, 600, 10), 10);

        screen.getLevel().setTile(Vector2D.of(0, 10), Registries.Tiles.INFECTED.getId());
        screen.getLevel().setTile(Vector2D.of(20, 10), Registries.Tiles.HEALTHY.getId());

        window.setContentPane(screen);
        window.setVisible(true);
    }
}
