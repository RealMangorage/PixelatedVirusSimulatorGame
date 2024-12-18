package org.mangorage.virusgame.level.tile;

import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.vector.Vector2D;
import org.mangorage.virusgame.level.tile.entity.TileEntity;

public interface ITileEntityTicker<T extends TileEntity> {
    void tick(Vector2D pos, IHolder<? extends Tile> tile, T tileEntity);
}
