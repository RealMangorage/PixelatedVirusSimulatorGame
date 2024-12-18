package org.mangorage.virusgame.level;

import org.mangorage.virusgame.registry.BuiltInRegistries;
import org.mangorage.virusgame.level.tile.IEntityTile;
import org.mangorage.virusgame.level.tile.ITileEntityTicker;
import org.mangorage.virusgame.level.tile.Tile;
import org.mangorage.virusgame.level.tile.entity.TileEntity;
import org.mangorage.virusgame.misc.ObjectByteBackedTracker;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.registry.Key;
import org.mangorage.virusgame.vector.Vector2D;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public final class Level {
    public interface IGridConsumer<T> {
        boolean accept(Vector2D vector2D, IHolder<? extends T> object);
    }

    public static Level create(int width, int height, int scale) {
        return new Level(
                (width / scale),
                (height / scale)
        );
    }

    private final Object lock = new Object();
    private final Random random = new Random();

    private final Map<Vector2D, TileEntity> tileEntityMap = new HashMap<>();
    private final Map<Vector2D, ITileEntityTicker<? extends TileEntity>> tickerMap = new HashMap<>();
    private final ObjectByteBackedTracker<Key> tileTracker = new ObjectByteBackedTracker<>();

    private final byte[][] grid;
    private final int sizeX, sizeY;
    private int ticks;


    private Level(int sizeX, int sizeY) {
        this.grid = new byte[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                setTile(Vector2D.of(x, y), BuiltInRegistries.Tiles.AIR);
            }
        }
    }

    public Random getRandom() {
        return random;
    }

    public void setTile(Vector2D pos, IHolder<? extends Tile> holder) {
        if (pos.x() >= sizeX || pos.x() < 0 || pos.y() >= sizeY || pos.y() < 0) return;
        synchronized (lock) {
            tileTracker.removeObjectById(grid[pos.x()][pos.y()]);
            this.grid[pos.x()][pos.y()] = tileTracker.addObject(holder.getId());

            if (holder.getValue() instanceof IEntityTile<?> entityTile) {
                var ticker = entityTile.createTicker();
                var tileEntity = entityTile.create(this, pos);

                tickerMap.put(pos, ticker);
                tileEntityMap.put(pos, tileEntity);
            } else {
                tileEntityMap.remove(pos);
                tickerMap.remove(pos);
            }
        }
    }

    public boolean hasAnyTile(IHolder<? extends Tile> actual) {
        return tileTracker.getAmount(actual.getId()) != -1;
    }

    public void forEach(IGridConsumer<Tile> consumer) {
        core: for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                var pos = Vector2D.of(x, y);
                var breakLoop = consumer.accept(
                        pos,
                        BuiltInRegistries.Tiles.REGISTRY.getObject(
                                tileTracker.getObjectById(
                                        grid[pos.x()][pos.y()]
                                )
                        )
                );
                if (breakLoop) break core;
            }
        }
    }

    public IHolder<? extends Tile> getTile(Vector2D pos) {
        if (pos.x() >= sizeX || pos.x() < 0 || pos.y() >= sizeY || pos.y() < 0)
            return BuiltInRegistries.Tiles.REGISTRY.getDefault();

        synchronized (lock) {
            return BuiltInRegistries.Tiles.REGISTRY.getObject(tileTracker.getObjectById(grid[pos.x()][pos.y()]));
        }
    }

    public Optional<TileEntity> getTileEntity(Vector2D pos) {
        return Optional.ofNullable(tileEntityMap.get(pos));
    }

    public <T extends TileEntity> Optional<T> getTileEntity(Vector2D pos, Class<T> tileEntityClass) {
        var entityOptional = getTileEntity(pos);
        if (entityOptional.isEmpty()) return Optional.empty();
        var entity = entityOptional.get();
        if (entity.getClass().isAssignableFrom(tileEntityClass))
            return Optional.of((T) entity);
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public void tick() {
        ticks++;

        if (!hasAnyTile(BuiltInRegistries.Tiles.INFECTED)) {
            System.out.println("Healthy eliminated Infected");
            System.exit(0);
        }
        if (!hasAnyTile(BuiltInRegistries.Tiles.HEALTHY)) {
            System.out.println("Infected eliminated Healthy");
            System.exit(0);
        }

        forEach((pos,tile) -> {
            if (tile.getValue().canTick()) tile.getValue().tick(this, pos, tile);
            ITileEntityTicker<TileEntity> ticker = (ITileEntityTicker<TileEntity>) tickerMap.get(pos);
            if (ticker != null)
                ticker.tick(
                        pos,
                        tile,
                        tileEntityMap.get(pos)
                );
            return false;
        });
    }

    public int getTicks() {
        return ticks;
    }
}
