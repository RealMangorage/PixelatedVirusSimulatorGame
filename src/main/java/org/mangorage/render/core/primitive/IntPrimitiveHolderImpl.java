package org.mangorage.render.core.primitive;

public final class IntPrimitiveHolderImpl implements IPrimitiveHolder {
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

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getType() {
        return (T) (Object) value;
    }
}
