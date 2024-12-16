package org.mangorage.render.core.primitive;

public interface IPrimitiveHolder {
    static IPrimitiveHolder of(byte value) {
        return new BytePrimitiveHolderImpl(value);
    }

    int getInt();
    short getShort();
    byte getByte();

    boolean hasType(Class<?> clazz);
}
