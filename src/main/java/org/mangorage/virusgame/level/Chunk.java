package org.mangorage.virusgame.level;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.mangorage.virusgame.level.tile.IEntityTile;
import org.mangorage.virusgame.level.tile.ITileEntityTicker;
import org.mangorage.virusgame.level.tile.Tile;
import org.mangorage.virusgame.level.tile.entity.TileEntity;
import org.mangorage.virusgame.misc.ObjectByteBackedTracker;
import org.mangorage.virusgame.registry.BuiltInRegistries;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.registry.Key;
import org.mangorage.virusgame.vector.ChunkPos;
import org.mangorage.virusgame.vector.Vector2D;

import java.util.Optional;

public final class Chunk {
    private final Object lock = new Object();
    private final byte[][] grid = new byte[16][16];
    private final ObjectByteBackedTracker<Key> tileTracker = new ObjectByteBackedTracker<>();
    private final Level level;

    private final Object2ObjectOpenHashMap<Vector2D, TileEntity> tileEntityMap = new Object2ObjectOpenHashMap<>();
    private final Object2ObjectOpenHashMap<Vector2D, ITileEntityTicker<? extends TileEntity>> tickerMap = new Object2ObjectOpenHashMap<>();

    Chunk(Level level) {
        this.level = level;
    }

    public void setTile(ChunkPos pos, IHolder<? extends Tile> holder) {
        synchronized (lock) {
            tileTracker.removeObjectById(grid[pos.relative().x()][pos.relative().y()]);
            this.grid[pos.relative().x()][pos.relative().y()] = tileTracker.addObject(holder.getId());

            if (holder.getValue() instanceof IEntityTile<?> entityTile) {
                tileEntityMap.put(pos.actual(), entityTile.create(level, pos.actual()));
                tickerMap.put(pos.actual(), entityTile.createTicker());
            } else {
                tileEntityMap.remove(pos.actual());
                tickerMap.remove(pos.actual());
            }
        }
    }

    public IHolder<? extends Tile> getTile(ChunkPos pos) {
        return BuiltInRegistries.Tiles.REGISTRY.getObject(
                tileTracker.getObjectById(grid[pos.relative().x()][pos.relative().y()])
        );
    }

    public Optional<ITileEntityTicker<? extends TileEntity>> getTicker(Vector2D actualPos) {
        return Optional.ofNullable(tickerMap.get(actualPos));
    }

    public Optional<TileEntity> getTileEntity(Vector2D actualPos) {
        return Optional.ofNullable(tileEntityMap.get(actualPos));
    }
}
