package org.mangorage.render.core.registry;

import java.lang.reflect.Array;
import java.util.Arrays;

public final class DefaultedRegistry<T> implements IRegistry<T> {
    @SuppressWarnings("unchecked")
    public static <T> T[] createGenericArray(Class<T> tClass, int length) {
        return (T[]) Array.newInstance(tClass, length);
    }



    private final Object lock = new Object();
    private final Class<T> tClass;
    private final byte defaultId;

    private T[] objects;
    private byte freeID = 0x00;

    public DefaultedRegistry(Class<T> objectClass, byte defaultID) {
        this.tClass = objectClass;
        this.objects = createGenericArray(objectClass, 0);
        this.defaultId = defaultID;
    }

    public <E extends T> RegistryObject<E> register(E object) {
        synchronized (lock) {
            T[] reference = this.objects;
            this.objects = Arrays.copyOf(reference, reference.length + 1);
            objects[freeID] = object;
            byte takenId = freeID;
            freeID++;
            return new RegistryObject<>(takenId, object);
        }
    }

    public T getDefault() {
        return objects[defaultId];
    }

    public T getObject(byte id) {
        synchronized (lock) {
            return objects[id];
        }
    }
}
