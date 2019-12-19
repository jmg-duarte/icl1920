package types;

public class TInt implements IType {

    public static final TInt TYPE = new TInt();

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TInt;
    }

    @Override
    public String getCompiledType() {
        return "I";
    }

    @Override
    public String getClosureType() {
        return "INT";
    }
}
