package org.mangorage.render.tile.entity;

import org.mangorage.render.Registries;
import org.mangorage.render.core.Level;
import org.mangorage.render.core.game.TileEntity;
import org.mangorage.render.core.vector.Direction;
import org.mangorage.render.core.vector.Vector2D;

public class InfectedTileEntity extends TileEntity {
    private int health = 0;
    private int ticks = 0;

    public InfectedTileEntity(Level level, Vector2D pos) {
        super(level, pos, Registries.Tiles.INFECTED);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void tick() {
        var level = getLevel();
        var pos = getPos();

        if (ticks % 20 == 0) {
            Direction dir = Direction.getRandom(level.getRandom());
            var newPos = dir.relative(pos, 1);
            var tile = level.getTile(newPos);
            var entity = level.getTileEntity(newPos);

            if (tile != getTileHolder()) {
                // Slowly infect healthy cells
                entity.ifPresent(e -> {
                    if (e instanceof HealthyTileEntity hte) {
                        health = health + hte.handleInfection(this);
                    }
                });

                // Handle growing
                if (health >= 20) {
                    health = health - 20;
                    level.setTile(newPos, getTileHolder());
                }
            }
        }
        ticks++;
    }
}
