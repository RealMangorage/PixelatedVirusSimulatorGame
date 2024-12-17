package org.mangorage.render.core.vector;

import java.util.Random;

public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1);

    public static Direction getRandom(Random random) {
        return values()[random.nextInt(Direction.values().length)];
    }

    private final int relativeX;
    private final int relitiveY;

    Direction(int x, int y) {
        this.relativeX = x;
        this.relitiveY = y;
    }

    public Vector2D relative(Vector2D pos, int distance) {
        return new Vector2D(
                pos.x() + (relativeX * distance),
                pos.y() + (relitiveY * distance)
        );
    }
}