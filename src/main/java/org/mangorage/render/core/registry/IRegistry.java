package org.mangorage.render.core.registry;

public interface IRegistry<T> {
    <E extends T> IHolder<E> register(Key key, E object);
    IHolder<? extends T> getDefault();
    IHolder<? extends T> getObject(Key id);
}
