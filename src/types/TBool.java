package types;

public class TBool implements IType {
    @Override
    public IType getType() {
        return new TBool();
    }

    @Override
    public String toString() {
        return "I";
    }

    @Override
    public boolean equals(Object object){
        return object instanceof TBool;
    }

}
