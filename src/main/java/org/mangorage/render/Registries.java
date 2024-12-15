package org.mangorage.render;

import org.mangorage.render.core.registry.DefaultedRegistry;
import org.mangorage.render.core.registry.RegistryObject;
import org.mangorage.render.tile.HealthyTile;
import org.mangorage.render.tile.EmptyTile;
import org.mangorage.render.tile.InfectedTile;
import org.mangorage.render.core.game.Tile;

public final class Registries {

    public final static class Tiles {
        public static final DefaultedRegistry<Tile> REGISTRY = new DefaultedRegistry<>(Tile.class, (byte) 0);

        public static final RegistryObject<EmptyTile> AIR = REGISTRY.register(new EmptyTile());
        public static final RegistryObject<HealthyTile> HEALTHY = REGISTRY.register(new HealthyTile());
        public static final RegistryObject<InfectedTile> INFECTED = REGISTRY.register(new InfectedTile());

        public static void bootstrap() {}
    }


    public static void bootstrap() {
        Tiles.bootstrap();
    }
}
