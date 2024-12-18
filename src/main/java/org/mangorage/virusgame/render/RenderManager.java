package org.mangorage.virusgame.render;

import org.mangorage.virusgame.level.Level;
import org.mangorage.virusgame.level.tile.Tile;
import org.mangorage.virusgame.misc.SharedConstants;
import org.mangorage.virusgame.registry.IHolder;
import org.mangorage.virusgame.registry.Key;
import org.mangorage.virusgame.vector.Vector2D;

import java.util.HashMap;
import java.util.Map;

public final class RenderManager<T> {
    private static final RenderManager<IRenderer<Level, Vector2D, IHolder<? extends Tile>>> INSTANCE = new RenderManager<>(
            (g, l, p, t) -> {
                g.setColor(t.getValue().getColor());
                g.fillRect(
                        p.x() * SharedConstants.scale,
                        p.y() * SharedConstants.scale,
                        SharedConstants.scale,
                        SharedConstants.scale
                );
            }
    );

    public static void bootstrap() {

    }

    public static RenderManager<IRenderer<Level, Vector2D, IHolder<? extends Tile>>> getTileRenderer() {
        return INSTANCE;
    }

    private final Map<Key, T> renderers = new HashMap<>();
    private final T defaultRenderer;

    private RenderManager(T defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }


    private void register(Key key, T renderer) {
        renderers.put(key, renderer);
    }

    public T getRenderer(Key key) {
        return renderers.getOrDefault(key, defaultRenderer);
    }
}
