package types;

public class TRef implements IType {
    private final IType contentType; //content type

    public TRef(IType cellType) {
        this.contentType = cellType;
    }

    @Override
    public IType getType() {
        return contentType;
    }

    @Override
    public String toString() {
        // return "Ref("+contentType.getType()+")";
        return "Ljava/lang/Object";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TRef && contentType.equals(((TRef) object).getType());
    }

}
