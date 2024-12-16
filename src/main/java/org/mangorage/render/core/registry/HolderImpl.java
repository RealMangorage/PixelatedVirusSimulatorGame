package org.mangorage.render.core.registry;

import org.mangorage.render.core.primitive.IPrimitiveHolder;

public final class HolderImpl<T> implements IHolder<T> {
    private final IPrimitiveHolder id;
    private final T value;

    HolderImpl(IPrimitiveHolder id, T value) {
        this.id = id;
        this.value = value;
    }

    public IPrimitiveHolder getId() {
        return id;
    }

    public T getValue() {
        return value;
    }
}
