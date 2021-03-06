package value;

public class VInt implements IValue {

    private final int value;

    public VInt(int value) {
        this.value = value;
    }

    public static VInt add(VInt v1, VInt v2) {
        return new VInt(v1.getValue() + v2.getValue());
    }

    public static VInt sub(VInt v1, VInt v2) {
        return new VInt(v1.getValue() - v2.getValue());
    }

    public static VInt mul(VInt v1, VInt v2) {
        return new VInt(v1.getValue() * v2.getValue());
    }

    public static VInt div(VInt v1, VInt v2) {
        return new VInt(v1.getValue() / v2.getValue());
    }

    public static VInt mod(VInt v1, VInt v2) {
        return new VInt(v1.getValue() % v2.getValue());
    }

    public static VInt negate(VInt v) {
        return new VInt(v.value);
    }

    public static VInt check(IValue value) {
        if (!(value instanceof VInt)) {
            throw new TypeErrorException();
        }
        return (VInt) value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VInt)) {
            throw new TypeErrorException();
        }
        return value == ((VInt) obj).value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
