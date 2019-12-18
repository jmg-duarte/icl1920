package types;

public class TRef implements IType {
    private final IType contentType; //content type

    public TRef(IType cellType) {
        this.contentType = cellType;
    }

    public IType getInnerType() {
        return contentType;
    }

    @Override
    public String toString() {
        return String.format("ref %s", contentType);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TRef && contentType.equals(((TRef) object).contentType);
    }

    @Override
    public String getCompiledType() {
        return "Ljava/lang/Object";
    }
}
