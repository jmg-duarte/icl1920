package value;

public class VBool implements IValue {

    private final boolean value;

    public VBool(boolean value) {
        this.value = value;
    }

    public static VBool and(VBool v1, VBool v2) {
        return new VBool(v1.value && v2.value);
    }

    public static VBool or(VBool v1, VBool v2) {
        return new VBool(v1.value || v2.value);
    }

    public static VBool check(IValue value) {
        if (!(value instanceof VBool)) {
            throw new TypeErrorException();
        }
        return (VBool) value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VBool)) {
            throw new TypeErrorException();
        }
        return value == ((VBool) obj).value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

}
