package org.mangorage.render;

import org.mangorage.render.core.registry.DefaultedRegistry;
import org.mangorage.render.core.registry.IHolder;
import org.mangorage.render.tile.HealthyTile;
import org.mangorage.render.tile.EmptyTile;
import org.mangorage.render.tile.InfectedTile;
import org.mangorage.render.core.game.Tile;

public final class Registries {

    public final static class Tiles {
        public static final DefaultedRegistry<Tile> REGISTRY = new DefaultedRegistry<>(Tile.class, (byte) 0);

        public static final IHolder<EmptyTile> AIR = REGISTRY.register(new EmptyTile());
        public static final IHolder<HealthyTile> HEALTHY = REGISTRY.register(new HealthyTile());
        public static final IHolder<InfectedTile> INFECTED = REGISTRY.register(new InfectedTile());

        public static void bootstrap() {}
    }


    public static void bootstrap() {
        Tiles.bootstrap();
    }
}
