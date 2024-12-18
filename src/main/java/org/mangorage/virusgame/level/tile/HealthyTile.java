package org.mangorage.virusgame.level.tile;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.level.tile.entity.TileEntity;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.render.IRenderer;
import org.mangorage.virusgame.vector.Shape;
import org.mangorage.virusgame.vector.Vector2D;
import org.mangorage.virusgame.level.tile.entity.HealthyTileEntity;

import java.awt.*;

public class HealthyTile extends Tile implements IEntityTile<HealthyTileEntity>, IRenderer<Level, Vector2D, IHolder<HealthyTile>> {

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public TileEntity create(Level level, Vector2D pos) {
        return new HealthyTileEntity(level, pos);
    }

    @Override
    public ITileEntityTicker<HealthyTileEntity> createTicker() {
        return ((pos, tile, tileEntity) -> {
            tileEntity.tick();
        });
    }


    @Override
    public void render(Graphics graphics, Level level, Vector2D vector2D, IHolder<HealthyTile> healthyTileIHolder) {

    }
}
