package org.mangorage.render.tile;

import org.mangorage.render.core.game.Tile;
import org.mangorage.render.core.vector.Shape;

import java.awt.*;

public class EmptyTile extends Tile {
    @Override
    public Shape getShape() {
        return new Shape(10, 10);
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public boolean canRender() {
        return false;
    }
}
