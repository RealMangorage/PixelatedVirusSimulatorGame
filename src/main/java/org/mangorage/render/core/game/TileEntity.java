package org.mangorage.render.core.game;

import org.mangorage.render.core.Level;
import org.mangorage.render.core.vector.Vector2D;

public abstract class TileEntity {
    private final Level level;
    private final Vector2D pos;
    private final byte id;

    public TileEntity(Level level, Vector2D pos, byte id) {
        this.level = level;
        this.pos = pos;
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public Vector2D getPos() {
        return pos;
    }

    public byte getId() {
        return id;
    }
}
