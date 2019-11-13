package value;

public class VRef implements IValue {

    private IValue value;

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
}
