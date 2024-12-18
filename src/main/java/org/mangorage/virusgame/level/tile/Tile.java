package org.mangorage.virusgame.level.tile;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.vector.Shape;
import org.mangorage.virusgame.vector.Vector2D;

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

    public void tick(Level level, Vector2D pos, IHolder<? extends Tile> tileHolder) {}
}
