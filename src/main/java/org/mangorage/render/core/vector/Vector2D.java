package org.mangorage.render.core.vector;

public record Vector2D(int x, int y) {
    public static Vector2D of(int x, int y) {
        return new Vector2D(x, y);
    }
}
