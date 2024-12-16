package org.mangorage.render.core.primitive;

public final class ShortPrimitiveHolderImpl implements IPrimitiveHolder {
    private final short value;

    ShortPrimitiveHolderImpl(short value) {
        this.value = value;
    }

    @Override
    public int getInt() {
        return 0;
    }

    @Override
    public short getShort() {
        return value;
    }

    @Override
    public byte getByte() {
        return 0;
    }

    @Override
    public boolean hasType(Class<?> clazz) {
        return clazz == short.class;
    }
}
