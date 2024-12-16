package org.mangorage.render.core.registry;

public final class HolderImpl<T> implements IHolder<T> {
    private final int id;
    private final T value;

    HolderImpl(int id, T value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public T getValue() {
        return value;
    }
}
