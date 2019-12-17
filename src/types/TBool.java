package types;

public class TBool implements IType {

    public static final TBool TYPE = new TBool();

    @Override
    public String toString() {
        return "I";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TBool;
    }

}
