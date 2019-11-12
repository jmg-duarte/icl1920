package value;

public class VBool implements IValue{

    private final boolean value;

    public VBool(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {return Boolean.toString(value);}
}
