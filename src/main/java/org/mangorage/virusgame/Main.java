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

        if (SharedConstants.resetWhenDead) {
            Game.getInstance().addTickable(() -> {
                if (!Game.getInstance().getLevel().hasAnyTile(BuiltInRegistries.Tiles.HEALTHY)) {
                    System.out.println("Infected eliminated Healthy");
                    setLevel(); // Reset
                }
                if (!Game.getInstance().getLevel().hasAnyTile(BuiltInRegistries.Tiles.INFECTED)) {
                    System.out.println("Healthy eliminated Infected");
                    setLevel(); // Reset
                }
            });
        }

        // Prepare Level for Game
        setLevel();

        // Init Gui
        SwingUtilities.invokeLater(RootScreen::initScreen);
    }

    public static void setLevel() {
        // Create level
        Level mainLevel = Level.create(SharedConstants.screenWidth, SharedConstants.screenHeight, SharedConstants.scale);

        // Prepare it
        Vector2D healthyPos = Vector2D.of(
               mainLevel.getRandom().nextInt(16),
               mainLevel.getRandom().nextInt(16)
        );

        Vector2D infectedPos = Vector2D.of(
                mainLevel.getRandom().nextInt(16),
                mainLevel.getRandom().nextInt(16)
        );

        var a = 1;

        if (SharedConstants.spawnInfected) {
            mainLevel.setTile(healthyPos, BuiltInRegistries.Tiles.INFECTED);
            mainLevel.getTileEntity(healthyPos, InfectedTileEntity.class).ifPresent(ite -> {
                ite.setHealth(160);
            });
        }

        if (SharedConstants.spawnHealthy) {
            mainLevel.setTile(infectedPos, BuiltInRegistries.Tiles.HEALTHY);
            mainLevel.getTileEntity(infectedPos, HealthyTileEntity.class).ifPresent(hte -> {
                hte.setHealth(360);
            });
        }

        // Init game
        Game.getInstance().setLevel(mainLevel);
    }
}
