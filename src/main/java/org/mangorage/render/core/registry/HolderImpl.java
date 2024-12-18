package org.mangorage.render.core.registry;


public final class HolderImpl<T> implements IHolder<T> {
    private final Key id;
    private final T value;

    HolderImpl(Key id, T value) {
        this.id = id;
        this.value = value;
    }

    public Key getId() {
        return id;
    }

    public T getValue() {
        return value;
    }
}
