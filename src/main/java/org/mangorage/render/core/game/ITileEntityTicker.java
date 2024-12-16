package org.mangorage.render.core.game;

import org.mangorage.render.core.registry.IHolder;
import org.mangorage.render.core.vector.Vector2D;

public interface ITileEntityTicker<T extends TileEntity> {
    void tick(Vector2D pos, IHolder<Tile> tile, T tileEntity);
}
