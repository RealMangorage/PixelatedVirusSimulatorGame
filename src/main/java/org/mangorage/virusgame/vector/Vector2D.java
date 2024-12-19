package org.mangorage.virusgame.vector;

import org.mangorage.virusgame.level.Chunk;

public record Vector2D(int x, int y) {
    public static Vector2D of(int x, int y) {
        return new Vector2D(x, y);
    }

    public ChunkPos toChunkPos() {
        int cX = x() / 16;
        int cY = y() / 16;

        int rX = x() - (16 * cX);
        int rY = y() - (16 * cY);

        return new ChunkPos(
                cX,
                cY,
                this,
                Vector2D.of(rX, rY)
        );
    }
}
