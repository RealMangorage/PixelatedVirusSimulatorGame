package org.mangorage.virusgame.level;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.mangorage.virusgame.level.tile.ITileEntityTicker;
import org.mangorage.virusgame.level.tile.Tile;
import org.mangorage.virusgame.level.tile.entity.TileEntity;
import org.mangorage.virusgame.misc.SharedConstants;
import org.mangorage.virusgame.registry.BuiltInRegistries;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.registry.Key;
import org.mangorage.virusgame.vector.ChunkPos;
import org.mangorage.virusgame.vector.Vector2D;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public final class Level {
    public interface IGridConsumer<T> {
        boolean accept(Vector2D vector2D, IHolder<? extends T> object);
    }

    public static Level create(int width, int height, int scale) {
        return new Level();
    }

    private final Object lock = new Object();
    private final Random random = new Random();

    private final int cSizeX = SharedConstants.chunkSizeX;
    private final int cSizeY = SharedConstants.chunkSizeY;

    private final int sizeX = (cSizeX * 16) - 1;
    private final int sizeY = (cSizeY * 16) - 1;


    private final Chunk[][] chunks = new Chunk[cSizeX][cSizeY];
    private final Object2ObjectMap<Key, AtomicInteger> tileTracker = new Object2ObjectOpenHashMap<>();

    private Level() {
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[x].length; y++) {
                chunks[x][y] = new Chunk(this);
            }
        }
        forEach((p, t) -> {
            setTile(p, BuiltInRegistries.Tiles.AIR);
            return false;
        });
    }

    public Random getRandom() {
        return random;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setTile(Vector2D pos, IHolder<? extends Tile> holder) {
        if (pos.x() < 0 || pos.y() < 0 || pos.x() > sizeX || pos.y() > sizeY)
            return;
        synchronized (lock) {
            var currentTile = getTile(pos);
            var tracker = tileTracker.get(currentTile.getId());
            if (tracker != null) {
                tracker.set(tracker.get() - 1);
                if (tracker.get() == 0 && holder != currentTile)
                    tileTracker.remove(currentTile.getId());
            }
            ChunkPos chunkPos = pos.toChunkPos();
            chunks[chunkPos.cX()][chunkPos.cY()].setTile(
                    chunkPos,
                    holder
            );
            tileTracker.computeIfAbsent(holder.getId(), k -> new AtomicInteger(0)).accumulateAndGet(1, Integer::sum);
        }
    }

    public boolean hasAnyTile(IHolder<? extends Tile> holder) {
        return tileTracker.containsKey(holder.getId());
    }

    public Chunk getChunk(ChunkPos pos) {
        if (pos.cX() < 0 || pos.cX() > cSizeX || pos.cY() < 0 || pos.cY() > cSizeY) return null;
        return chunks[pos.cX()][pos.cY()];
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
        if (chunk == null) return Optional.empty();
        return Optional.ofNullable(chunk.getTileEntity(pos));
    }

    public <T extends TileEntity> Optional<T> getTileEntity(Vector2D pos, Class<T> tileEntityClass) {
        var chunk = getChunk(pos.toChunkPos());
        if (chunk == null) return Optional.empty();
        return Optional.ofNullable((T) chunk.getTileEntity(pos));
    }

    public void tick() {
        forEach((p, t) -> {
            var chunk = getChunk(p.toChunkPos());
            if (chunk != null) {
                var tileEntity = chunk.getTileEntity(p);
                var tileTicker = (ITileEntityTicker<TileEntity>) chunk.getTicker(p);

                if (tileEntity != null)
                    tileTicker.tick(p, t, tileEntity);
            }
            return false;
        });
    }
}
