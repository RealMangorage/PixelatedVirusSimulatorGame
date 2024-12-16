package org.mangorage.render.core.registry;


import org.mangorage.render.core.primitive.IPrimitiveHolder;

import java.util.Arrays;

public final class DefaultedByteBackedRegistry<T> implements IRegistry<T> {

    public static <T> DefaultedByteBackedRegistry<T> create(Class<T> tClass, byte defaultId) {
        return new DefaultedByteBackedRegistry<>(tClass, defaultId);
    }


    private final Object lock = new Object();
    private final Class<T> tClass;
    private final byte defaultId;

    private IHolder<T>[] objects;
    private byte freeID = 0;

    public DefaultedByteBackedRegistry(Class<T> objectClass, byte defaultID) {
        this.tClass = objectClass;
        this.objects = new IHolder[0];
        this.defaultId = defaultID;
    }

    @Override
    public <E extends T> IHolder<E> register(E object) {
        synchronized (lock) {
            IHolder<T>[] reference = this.objects;
            this.objects = Arrays.copyOf(reference, reference.length + 1);
            var takenId = freeID;
            IHolder<T> holder = new HolderImpl<>(takenId, object);
            objects[freeID] = holder;
            freeID++;
            return new HolderImpl<>(takenId, object);
        }
    }

    @Override
    public IHolder<T> getDefault() {
        return objects[defaultId];
    }

    @Override
    public IHolder<T> getObject(IPrimitiveHolder id) {
        if (id.getByte() >= objects.length || id.getByte() < 0)
            return getDefault();
        synchronized (lock) {
            return objects[id.getByte()];
        }
    }
}
