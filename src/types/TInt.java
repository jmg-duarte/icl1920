package types;

public class TInt implements IType {

    @Override
    public IType getType() {
        return new TInt();
    }

    @Override
    public String toString() {
        return "I";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TInt;
    }
}
