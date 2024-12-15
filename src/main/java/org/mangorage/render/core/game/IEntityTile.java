package org.mangorage.render.core.game;

import org.mangorage.render.core.Level;
import org.mangorage.render.core.vector.Vector2D;

public interface IEntityTile<T extends TileEntity> {
    TileEntity create(Level level, Vector2D pos);

    default ITileEntityTicker<T> createTicker() {
        return null;
    }
}

