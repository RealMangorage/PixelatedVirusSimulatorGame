package org.mangorage.virusgame.level.tile;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.vector.Vector2D;
import org.mangorage.virusgame.level.tile.entity.TileEntity;

public interface IEntityTile<T extends TileEntity> {
    TileEntity create(Level level, Vector2D pos);

    default ITileEntityTicker<T> createTicker() {
        return null;
    }
}

