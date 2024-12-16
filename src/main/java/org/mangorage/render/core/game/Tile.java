package org.mangorage.render.core.game;

import org.mangorage.render.core.Level;
import org.mangorage.render.core.registry.IHolder;
import org.mangorage.render.core.vector.Shape;
import org.mangorage.render.core.vector.Vector2D;

import java.awt.*;

public abstract class Tile {
    abstract public Shape getShape();
    abstract public Color getColor();

    public boolean canRender() {
        return true;
    }

    public boolean canTick() {
        return false;
    }

    public void tick(Level level, Vector2D pos, IHolder<Tile> tileHolder) {}
}
