package types;

public class TRef implements IType {
    IType cellType;

    public TRef(IType cellType){
        this.cellType = cellType;
    }

    @Override
    public String getType() {
        return "Ref("+cellType.getType()+")";
    }

    @Override
    public String toString() {
        return "ref";
        //"LJava/lang/Object";

    }

}
