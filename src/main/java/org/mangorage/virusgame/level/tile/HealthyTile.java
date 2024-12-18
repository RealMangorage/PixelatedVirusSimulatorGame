package org.mangorage.virusgame.level.tile;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.level.tile.entity.TileEntity;
import org.mangorage.virusgame.vector.Shape;
import org.mangorage.virusgame.vector.Vector2D;
import org.mangorage.virusgame.level.tile.entity.HealthyTileEntity;

import java.awt.*;

public class HealthyTile extends Tile implements IEntityTile<HealthyTileEntity> {

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public TileEntity create(Level level, Vector2D pos) {
        return new HealthyTileEntity(level, pos);
    }

    @Override
    public ITileEntityTicker<HealthyTileEntity> createTicker() {
        return ((pos, tile, tileEntity) -> {
            tileEntity.tick();
        });
    }
}
