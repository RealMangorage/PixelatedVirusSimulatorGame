package org.mangorage.render.core.registry;

import org.mangorage.render.core.primitive.IPrimitiveHolder;

public interface IHolder<T> {
    T getValue();
    IPrimitiveHolder getId();

    default boolean is(IHolder<?> holder) {
        return holder.getValue() == getValue();
    }
}
