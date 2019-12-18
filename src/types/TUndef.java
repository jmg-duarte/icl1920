package types;

public class TUndef implements IType {

    public static final TUndef TYPE = new TUndef();

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TUndef;
    }

    @Override
    public String getCompiledType() {
        throw new RuntimeException("not implemented");
    }
}
