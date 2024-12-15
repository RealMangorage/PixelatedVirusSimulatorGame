package org.mangorage.render.tile;

import org.mangorage.render.core.Level;
import org.mangorage.render.core.game.IEntityTile;
import org.mangorage.render.core.game.ITileEntityTicker;
import org.mangorage.render.core.game.Tile;
import org.mangorage.render.core.game.TileEntity;
import org.mangorage.render.core.vector.Shape;
import org.mangorage.render.core.vector.Vector2D;
import org.mangorage.render.tile.entity.InfectedTileEntity;

import java.awt.*;

public class InfectedTile extends Tile implements IEntityTile<InfectedTileEntity> {
    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public boolean canRender() {
        return true;
    }

    @Override
    public boolean canTick() {
        return true;
    }

    @Override
    public void tick(Level level, Vector2D pos, byte id) {
    }

    @Override
    public TileEntity create(Level level, Vector2D pos) {
        return new InfectedTileEntity(level, pos);
    }

    @Override
    public ITileEntityTicker<InfectedTileEntity> createTicker() {
        return ((pos, id, tile, tileEntity) -> {
           tileEntity.tick();
        });
    }
}
