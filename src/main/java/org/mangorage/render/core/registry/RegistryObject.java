package org.mangorage.render.core.registry;

public class RegistryObject<T> {
    private final byte id;
    private final T value;

    RegistryObject(byte id, T value) {
        this.id = id;
        this.value = value;
    }

    public byte getId() {
        return id;
    }

    public T getValue() {
        return value;
    }
}
