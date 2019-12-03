package types;

public class TInt implements IType{

    @Override
    public String getType() {
        return "Int";
    }

    @Override
    public String toString() {return getType();}
}
