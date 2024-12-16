package org.mangorage.render.core.registry;

public interface IHolder<T> {
    T getValue();
    int getId();

    default boolean is(IHolder<?> holder) {
        return holder.getValue() == getValue();
    }
}
