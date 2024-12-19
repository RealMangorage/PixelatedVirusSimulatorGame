package org.mangorage.virusgame.level;

import org.mangorage.virusgame.level.tile.ITileEntityTicker;
import org.mangorage.virusgame.level.tile.Tile;
import org.mangorage.virusgame.level.tile.entity.TileEntity;
import org.mangorage.virusgame.registry.BuiltInRegistries;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.vector.ChunkPos;
import org.mangorage.virusgame.vector.Vector2D;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public final class Level {
    public interface IGridConsumer<T> {
        boolean accept(Vector2D vector2D, IHolder<? extends T> object);
    }

    public static Level create(int width, int height, int scale) {
        return new Level();
    }

    private final Object lock = new Object();
    private final Random random = new Random();

    private final int cSizeX = 2;
    private final int cSizeY = 2;

    private final int sizeX = cSizeX * 16;
    private final int sizeY = cSizeY * 16;


    private final Chunk[][] chunks = new Chunk[cSizeX][cSizeY];

    private Level() {
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[x].length; y++) {
                chunks[x][y] = new Chunk(this);
            }
        }
        forEach((p, t) -> {
            setTile(p, t);
            return false;
        });
    }

    public Random getRandom() {
        return random;
    }

    public void setTile(Vector2D pos, IHolder<? extends Tile> holder) {
        if (pos.x() < 0 || pos.y() < 0 || pos.x() > sizeX || pos.y() > sizeY)
            return;
        synchronized (lock) {
            ChunkPos chunkPos = pos.toChunkPos();
            chunks[chunkPos.cX()][chunkPos.cY()].setTile(
                    chunkPos,
                    holder
            );
        }
    }

    public boolean hasAnyTile(IHolder<? extends Tile> actual) {
        return true;
    }

    public Optional<Chunk> getChunk(ChunkPos pos) {
        if (pos.cX() < 0 || pos.cX() > cSizeX || pos.cY() < 0 || pos.cY() > cSizeY)
            return Optional.empty();
        return Optional.of(chunks[pos.cX()][pos.cY()]);
    }

    public void forEach(IGridConsumer<Tile> consumer) {
        core: for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                var pos = Vector2D.of(x, y);
                var breakLoop = consumer.accept(
                        pos,
                        getTile(pos)
                );
                if (breakLoop) break core;
            }
        }
    }

    public IHolder<? extends Tile> getTile(Vector2D pos) {
        if (pos.x() < 0 || pos.y() < 0 || pos.x() > sizeX || pos.y() > sizeY)
            return BuiltInRegistries.Tiles.REGISTRY.getDefault();
        synchronized (lock) {
            ChunkPos chunkPos = pos.toChunkPos();
            return chunks[chunkPos.cX()][chunkPos.cY()].getTile(chunkPos);
        }
    }

    public Optional<TileEntity> getTileEntity(Vector2D pos) {
        var chunk = getChunk(pos.toChunkPos());
        return chunk.flatMap(value -> value.getTileEntity(pos));
    }

    public <T extends TileEntity> Optional<T> getTileEntity(Vector2D pos, Class<T> tileEntityClass) {
        var chunk = getChunk(pos.toChunkPos());
        return chunk.flatMap(value -> (Optional<T>) value.getTileEntity(pos));
    }

    public void tick() {

        forEach((p, t) -> {
            var chunkOptional = getChunk(p.toChunkPos());
            if (chunkOptional.isPresent()) {
                var chunk = chunkOptional.get();
                var tileEntityOptional = chunk.getTileEntity(p);
                if (tileEntityOptional.isPresent()) {
                    var tileEntity = tileEntityOptional.get();
                    chunk.getTicker(p).ifPresent(ticker -> {
                        ((ITileEntityTicker<TileEntity>) ticker).tick(p, t, tileEntity);
                    });
                }
            }
            return false;
        });
    }
}
