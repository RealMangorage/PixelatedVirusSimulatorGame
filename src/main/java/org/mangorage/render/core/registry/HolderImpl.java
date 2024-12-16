package org.mangorage.render.core.registry;

public final class HolderImpl<T> implements IHolder<T> {
    private final byte id;
    private final T value;

    HolderImpl(byte id, T value) {
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
