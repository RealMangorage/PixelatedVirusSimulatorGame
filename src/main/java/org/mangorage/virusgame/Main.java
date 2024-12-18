package org.mangorage.virusgame;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.level.tile.entity.HealthyTileEntity;
import org.mangorage.virusgame.level.tile.entity.InfectedTileEntity;
import org.mangorage.virusgame.misc.SharedConstants;
import org.mangorage.virusgame.registry.BuiltInRegistries;
import org.mangorage.virusgame.render.RenderManager;
import org.mangorage.virusgame.vector.Vector2D;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        BuiltInRegistries.bootstrap();
        RenderManager.bootstrap();

        // Create level
        Level mainLevel = Level.create(SharedConstants.screenWidth, SharedConstants.screenHeight, SharedConstants.scale);

        // Prepare it
        mainLevel.setTile(Vector2D.of(0, 0), BuiltInRegistries.Tiles.INFECTED);
        mainLevel.getTileEntity(Vector2D.of(0, 0), InfectedTileEntity.class).ifPresent(ite -> {
            ite.setHealth(160);
        });

        mainLevel.setTile(Vector2D.of(12, 1), BuiltInRegistries.Tiles.HEALTHY);
        mainLevel.getTileEntity(Vector2D.of(0, 0), HealthyTileEntity.class).ifPresent(hte -> {
            hte.setHealth(360);
        });

        // Init game
        Game.initGame(mainLevel);

        // Init Gui
        SwingUtilities.invokeLater(RootScreen::initScreen);
    }
}
