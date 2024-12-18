package org.mangorage.render;

import org.mangorage.render.core.registry.DefaultedRegistry;
import org.mangorage.render.core.registry.IHolder;
import org.mangorage.render.core.registry.IRegistry;
import org.mangorage.render.core.registry.Key;
import org.mangorage.render.tile.HealthyTile;
import org.mangorage.render.tile.EmptyTile;
import org.mangorage.render.tile.InfectedTile;
import org.mangorage.render.core.game.Tile;

public final class Registries {
    private static Key create(String id) {
        return new Key("mod", id);
    }

    public final static class Tiles {
        public static final IRegistry<Tile> REGISTRY = new DefaultedRegistry<>(Tile.class, create("air"));

        public static final IHolder<EmptyTile> AIR = REGISTRY.register(create("air"), new EmptyTile());
        public static final IHolder<HealthyTile> HEALTHY = REGISTRY.register(create("healhty"), new HealthyTile());
        public static final IHolder<InfectedTile> INFECTED = REGISTRY.register(create("infected"), new InfectedTile());

        public static void bootstrap() {}
    }


    public static void bootstrap() {
        Tiles.bootstrap();
    }
}
