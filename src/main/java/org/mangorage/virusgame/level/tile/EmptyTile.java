package org.mangorage.virusgame.level.tile;

import org.mangorage.virusgame.vector.Shape;

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
