package org.mangorage.render.core;

import org.mangorage.render.Registries;
import org.mangorage.render.core.game.IEntityTile;
import org.mangorage.render.core.game.ITileEntityTicker;
import org.mangorage.render.core.game.Tile;
import org.mangorage.render.core.game.TileEntity;
import org.mangorage.render.core.vector.Vector2D;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Level {
    public interface IGridConsumer<T> {
        boolean accept(Vector2D vector2D, byte id, T object);
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

    private final byte[][] grid;
    private final int sizeX, sizeY;
    private int ticks;


    public Level(int sizeX, int sizeY) {
        this.grid = new byte[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Random getRandom() {
        return random;
    }

    public void setTile(Vector2D pos, byte id) {
        if (pos.x() >= sizeX || pos.x() < 0 || pos.y() >= sizeY || pos.y() < 0)
            return;
        synchronized (lock) {
            this.grid[pos.x()][pos.y()] = id;

            var tile = Registries.Tiles.REGISTRY.getObject(id);
            if (tile instanceof IEntityTile<?> entityTile) {
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

    public byte getTileId(Vector2D pos) {
        synchronized (lock) {
            return grid[pos.x()][pos.y()];
        }
    }

    public boolean hasAnyTile(byte id) {
        AtomicBoolean has = new AtomicBoolean(false);
        forEach((pos, id2, tile) -> {
            if (id2 == id) {
                has.set(true);
                return true;
            }
            return false;
        });
        return has.get();
    }

    public void forEach(IGridConsumer<Tile> consumer) {
        core: for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                var pos = Vector2D.of(x, y);
                var id = getTileId(pos);
                var breakLoop = consumer.accept(pos, id, Registries.Tiles.REGISTRY.getObject(id));
                if (breakLoop)
                    break core;
            }
        }
    }

    public Tile getTile(Vector2D pos) {
        if (pos.x() >= sizeX || pos.x() < 0 || pos.y() >= sizeY || pos.y() < 0)
            return Registries.Tiles.REGISTRY.getDefault();
        return Registries.Tiles.REGISTRY.getObject(
                getTileId(pos)
        );
    }

    public Optional<TileEntity> getTileEntity(Vector2D pos) {
        return Optional.ofNullable(tileEntityMap.get(pos));
    }

    @SuppressWarnings("unchecked")
    public void tick() {
        ticks++;

        if (!hasAnyTile(Registries.Tiles.INFECTED.getId())) {
            System.out.println("Infected eliminated Healthy");
            System.exit(0);
        }
        if (!hasAnyTile(Registries.Tiles.HEALTHY.getId())) {
            System.out.println("Healthy eliminated Infected");
            System.exit(0);
        }

        forEach((pos, id, tile) -> {
            if (tile.canTick())
                tile.tick(this, pos, id);
            ITileEntityTicker<TileEntity> ticker = (ITileEntityTicker<TileEntity>) tickerMap.get(pos);
            if (ticker != null)
                ticker.tick(
                        pos,
                        id,
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
