package org.mangorage.render.core.registry;

import org.mangorage.render.core.primitive.IPrimitiveHolder;

public interface IRegistry<T> {
    <E extends T> IHolder<E> register(E object);
    IHolder<T> getDefault();
    IHolder<T> getObject(IPrimitiveHolder id);
}
