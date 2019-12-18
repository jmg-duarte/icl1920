package value;

public class VUndef implements IValue {

    public static final VUndef UNDEFINED = new VUndef();

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VUndef;
    }

    @Override
    public String toString() {
        return "()";
    }
}
