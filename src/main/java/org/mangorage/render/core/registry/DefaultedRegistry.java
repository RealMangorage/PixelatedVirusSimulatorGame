package org.mangorage.render.core.registry;


import java.util.Arrays;

public final class DefaultedRegistry<T> implements IRegistry<T> {

    public static <T> DefaultedRegistry<T> create(Class<T> tClass, int defaultId) {
        return new DefaultedRegistry<>(tClass, defaultId);
    }


    private final Object lock = new Object();
    private final Class<T> tClass;
    private final int defaultId;

    private IHolder<T>[] objects;
    private int freeID = 0;

    public DefaultedRegistry(Class<T> objectClass, int defaultID) {
        this.tClass = objectClass;
        this.objects = new IHolder[0];
        this.defaultId = defaultID;
    }

    public <E extends T> IHolder<E> register(E object) {
        synchronized (lock) {
            IHolder<T>[] reference = this.objects;
            this.objects = Arrays.copyOf(reference, reference.length + 1);
            int takenId = freeID;
            IHolder<T> holder = new HolderImpl<>(takenId, object);
            objects[freeID] = holder;
            freeID++;
            return new HolderImpl<>(takenId, object);
        }
    }

    public IHolder<T> getDefault() {
        return objects[defaultId];
    }

    public IHolder<T> getObject(int id) {
        if (id >= objects.length || id < 0)
            return getDefault();
        synchronized (lock) {
            return objects[id];
        }
    }
}
