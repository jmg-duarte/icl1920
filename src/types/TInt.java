package types;

public class TInt implements IType {

    public static final TInt TYPE = new TInt();

    @Override
    public String toString() {
        return "I";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TInt;
    }
}
