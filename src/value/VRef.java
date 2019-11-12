package value;

public class VRef implements IValue {

    private IValue value;

    public IValue get() {
        return value;
    }

    public void set(IValue value) {
        this.value = value;
    }
}
