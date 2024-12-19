package org.mangorage.virusgame.registry;



import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public final class DefaultedRegistry<T> implements IRegistry<T> {

    public static <T> DefaultedRegistry<T> create(Class<T> tClass, Key defaultId) {
        return new DefaultedRegistry<>(tClass, defaultId);
    }


    private final Object lock = new Object();
    private final Class<T> tClass;

    private final Object2ObjectMap<Key, IHolder<? extends T>> registered = new Object2ObjectOpenHashMap<>();
    private final Key defaultId;

    public DefaultedRegistry(Class<T> objectClass, Key defaultID) {
        this.tClass = objectClass;
        this.defaultId = defaultID;
    }

    @Override
    public <E extends T> IHolder<E> register(Key key, E object) {
        synchronized (lock) {
            IHolder<E> holder = new HolderImpl<>(key, object);
            registered.put(key, holder);
            return holder;
        }
    }

    @Override
    public IHolder<? extends T> getDefault() {
        return registered.get(defaultId);
    }

    @Override
    public IHolder<? extends T> getObject(Key id) {
        synchronized (lock) {
            return registered.getOrDefault(id, registered.get(defaultId));
        }
    }
}
