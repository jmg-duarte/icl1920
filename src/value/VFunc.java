package value;

public class VFunc implements IValue {

    public static VFunc check(IValue value) {
        if (!(value instanceof VFunc)) {
            throw new TypeErrorException();
        }
        return (VFunc) value;
    }

}
