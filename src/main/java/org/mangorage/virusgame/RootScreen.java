package org.mangorage.virusgame;

import javax.swing.*;

public class RootScreen {
    public static void initScreen() {
        JFrame window = new JFrame("Pixelated Virus Simulator!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(810, 634);
        window.setResizable(true);

        RenderScreen screen = new RenderScreen();

        window.setContentPane(screen);
        window.setVisible(true);
    }
}
