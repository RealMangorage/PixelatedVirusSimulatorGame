package org.mangorage.virusgame.level.tile;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.level.tile.entity.TileEntity;
import org.mangorage.virusgame.render.IRenderer;
import org.mangorage.virusgame.vector.Shape;
import org.mangorage.virusgame.vector.Vector2D;
import org.mangorage.virusgame.level.tile.entity.InfectedTileEntity;

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
    public TileEntity create(Level level, Vector2D pos) {
        return new InfectedTileEntity(level, pos);
    }

    @Override
    public ITileEntityTicker<InfectedTileEntity> createTicker() {
        return ((pos, tile, tileEntity) -> {
           tileEntity.tick();
        });
    }
}
