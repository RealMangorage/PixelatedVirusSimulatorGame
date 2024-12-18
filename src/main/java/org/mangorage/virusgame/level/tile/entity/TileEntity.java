package org.mangorage.virusgame.level.tile.entity;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.vector.Vector2D;
import org.mangorage.virusgame.level.tile.Tile;

public abstract class TileEntity {
    private final Level level;
    private final Vector2D pos;
    private final IHolder<? extends Tile> tileHolder;

    public TileEntity(Level level, Vector2D pos, IHolder<? extends Tile> tileHolder) {
        this.level = level;
        this.pos = pos;
        this.tileHolder = tileHolder;
    }

    public Level getLevel() {
        return level;
    }

    public Vector2D getPos() {
        return pos;
    }

    public IHolder<? extends Tile> getTileHolder() {
        return tileHolder;
    }
}
