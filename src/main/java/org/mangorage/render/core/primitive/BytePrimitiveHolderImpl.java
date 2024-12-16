package org.mangorage.render.core.primitive;

public final class BytePrimitiveHolderImpl implements IPrimitiveHolder {
    private final byte value;

    BytePrimitiveHolderImpl(byte value) {
        this.value = value;
    }

    @Override
    public int getInt() {
        return 0;
    }

    @Override
    public short getShort() {
        return 0;
    }

    @Override
    public byte getByte() {
        return value;
    }

    @Override
    public boolean hasType(Class<?> clazz) {
        return clazz == byte.class;
    }
}
