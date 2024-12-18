package org.mangorage.virusgame.registry;

import org.mangorage.virusgame.level.tile.HealthyTile;
import org.mangorage.virusgame.level.tile.EmptyTile;
import org.mangorage.virusgame.level.tile.InfectedTile;
import org.mangorage.virusgame.level.tile.Tile;

public final class BuiltInRegistries {
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
