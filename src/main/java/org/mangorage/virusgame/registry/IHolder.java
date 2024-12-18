package org.mangorage.virusgame.registry;


public interface IHolder<T> {
    T getValue();
    Key getId();

    default boolean is(IHolder<?> holder) {
        return holder == this;
    }
}
