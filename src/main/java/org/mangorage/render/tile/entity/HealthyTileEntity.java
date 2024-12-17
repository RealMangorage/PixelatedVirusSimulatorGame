package org.mangorage.render.tile.entity;

import org.mangorage.render.Registries;
import org.mangorage.render.core.Level;
import org.mangorage.render.core.game.TileEntity;
import org.mangorage.render.core.vector.Direction;
import org.mangorage.render.core.vector.Vector2D;

public class HealthyTileEntity extends TileEntity {
    private int health = 0;
    private int ticks = 0;

    public HealthyTileEntity(Level level, Vector2D pos) {
        super(level, pos, Registries.Tiles.HEALTHY);
    }


    public int handleInfection(InfectedTileEntity ite) {
        health = health - 10;
        if (health < 0) health = 0;
        if (health == 0)
            getLevel().setTile(getPos(), ite.getTileHolder());
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void tick() {
        var level = getLevel();
        var pos = getPos();

        if (ticks % 10 == 0)
            health++;

        if (ticks % 20 == 0 && health >= 20) {
            Direction dir = Direction.getRandom(level.getRandom());
            var newPos = dir.relative(pos, 1);
            var tile = level.getTile(newPos);
            var entity = level.getTileEntity(newPos);

            if (tile != getTileHolder()) {
                health = health - 20;
                level.setTile(newPos, getTileHolder());
            }
        }
        ticks++;
    }

}
