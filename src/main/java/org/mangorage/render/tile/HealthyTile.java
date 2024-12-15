package org.mangorage.render.tile;

import org.mangorage.render.core.Level;
import org.mangorage.render.core.game.IEntityTile;
import org.mangorage.render.core.game.ITileEntityTicker;
import org.mangorage.render.core.game.Tile;
import org.mangorage.render.core.game.TileEntity;
import org.mangorage.render.core.vector.Shape;
import org.mangorage.render.core.vector.Vector2D;
import org.mangorage.render.tile.entity.HealthyTileEntity;

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
        return ((pos, id, tile, tileEntity) -> {
            tileEntity.tick();
        });
    }
}
