package org.mangorage.render.core.registry;

import org.mangorage.render.core.primitive.IPrimitiveHolder;

import java.util.Arrays;

public final class DefaultedIntBackedRegistry<T> implements IRegistry<T> {

    public static <T> DefaultedIntBackedRegistry<T> create(Class<T> tClass, int defaultId) {
        return new DefaultedIntBackedRegistry<>(tClass, defaultId);
    }


    private final Object lock = new Object();
    private final Class<T> tClass;
    private final int defaultId;

    private IHolder<? extends T>[] objects;
    private int freeID = 0;

    public DefaultedIntBackedRegistry(Class<T> objectClass, int defaultID) {
        this.tClass = objectClass;
        this.objects = new IHolder[0];
        this.defaultId = defaultID;
    }

    @Override
    public <E extends T> IHolder<E> register(E object) {
        synchronized (lock) {
            IHolder<? extends T>[] reference = this.objects;
            this.objects = Arrays.copyOf(reference, reference.length + 1);
            var takenId = freeID;
            IHolder<E> holder = new HolderImpl<>(IPrimitiveHolder.of(takenId), object);
            objects[freeID] = holder;
            freeID++;
            return holder;
        }
    }

    @Override
    public IHolder<? extends T> getDefault() {
        return objects[defaultId];
    }

    @Override
    public IHolder<? extends T> getObject(IPrimitiveHolder id) {
        if (id.getInt() >= objects.length || id.getInt() < 0)
            return getDefault();
        synchronized (lock) {
            return objects[id.getInt()];
        }
    }
}
