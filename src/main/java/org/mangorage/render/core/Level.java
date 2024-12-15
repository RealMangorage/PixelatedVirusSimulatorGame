package org.mangorage.render.core;

import org.mangorage.render.Registries;
import org.mangorage.render.core.game.IEntityTile;
import org.mangorage.render.core.game.ITileEntityTicker;
import org.mangorage.render.core.game.Tile;
import org.mangorage.render.core.game.TileEntity;
import org.mangorage.render.core.vector.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public final class Level {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Square JFrame with Border");

        // Set the size of the window (square)
        frame.setSize(400, 400); // Width and height are equal

        // Set the layout for the frame
        frame.setLayout(new BorderLayout());

        // Create a panel to serve as a custom border
        JPanel borderPanel = new JPanel();
        borderPanel.setLayout(new BorderLayout());
        borderPanel.setBackground(Color.GRAY);  // Border color

        // Create the content panel that you want to display inside the border
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.CYAN);  // Content color
        contentPanel.setPreferredSize(new Dimension(350, 350)); // Smaller than frame size

        // Add the content panel inside the border panel
        borderPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the border panel to the frame
        frame.add(borderPanel, BorderLayout.CENTER);

        // Make sure the window closes when the user exits
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the window to be non-resizable to maintain the square shape
        frame.setResizable(false);

        // Make the frame visible
        frame.setVisible(true);
    }
    public interface IGridConsumer<T> {
        void accept(Vector2D vector2D, byte id, T object);
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

    public void forEach(IGridConsumer<Tile> consumer) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                var pos = Vector2D.of(x, y);
                var id = getTileId(pos);
                consumer.accept(pos, id, Registries.Tiles.REGISTRY.getObject(id));
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
        });
    }

    public int getTicks() {
        return ticks;
    }
}
