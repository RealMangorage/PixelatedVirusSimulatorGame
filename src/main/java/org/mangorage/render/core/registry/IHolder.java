package org.mangorage.render.core.registry;


public interface IHolder<T> {
    T getValue();
    Key getId();

    default boolean is(IHolder<?> holder) {
        return holder == this;
    }
}
