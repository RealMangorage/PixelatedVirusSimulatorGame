package org.mangorage.render.tile.entity;

import org.mangorage.render.Registries;
import org.mangorage.render.core.Level;
import org.mangorage.render.core.game.TileEntity;
import org.mangorage.render.core.vector.Direction;
import org.mangorage.render.core.vector.Vector2D;

public class InfectedTileEntity extends TileEntity {
    public static final boolean ENABLED = true;
    private int ticks = 0;

    public InfectedTileEntity(Level level, Vector2D pos) {
        super(level, pos, Registries.Tiles.INFECTED);
    }

    public void tick() {
        if (!ENABLED) return;
        var level = getLevel();
        var pos = getPos();

        if (ticks % 90 == 0) {
            for (Direction direction : Direction.values()) {
                if (!level.getTile(direction.relative(pos, 1)).is(Registries.Tiles.HEALTHY) && level.getRandom().nextInt(10) < 4) {
                    level.setTile(direction.relative(pos, 1), Registries.Tiles.INFECTED);
                    break;
                }
            }
        }
        ticks++;
    }
}
