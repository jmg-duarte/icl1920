package value;

public class VInt implements IValue {

    private final int value;

    public VInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
