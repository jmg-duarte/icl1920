package value;

public class VRef implements IValue {

    private IValue value;

    public VRef(IValue value) {
        this.value = value;
    }

    public IValue get() {
        return value;
    }

    public void set(IValue value) {
        this.value = value;
    }

    public static VRef check(IValue value) {
        if (!(value instanceof VRef)) {
            throw new TypeErrorException();
        }
        return (VRef) value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VRef)) {
            throw new TypeErrorException();
        }
        return value.equals(((VRef) obj).value);
    }
}
