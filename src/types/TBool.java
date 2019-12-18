package types;

public class TBool implements IType {

    public static final TBool TYPE = new TBool();

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TBool;
    }

    @Override
    public String getCompiledType() {
        return "Z";
    }
}
