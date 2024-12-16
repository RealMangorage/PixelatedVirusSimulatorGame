package org.mangorage.render.core.primitive;

public class IntPrimitiveHolderImpl implements IPrimitiveHolder {
    private final int value;

    public IntPrimitiveHolderImpl(int value) {
        this.value = value;
    }

    @Override
    public int getInt() {
        return value;
    }

    @Override
    public short getShort() {
        return 0;
    }

    @Override
    public byte getByte() {
        return 0;
    }

    @Override
    public boolean hasType(Class<?> clazz) {
        return clazz == int.class;
    }
}
