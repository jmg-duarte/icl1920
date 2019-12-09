package types;

import java.util.List;

public class TFun implements IType {

    List<IType> paramTypes;
    IType bodyType;

    public TFun(List<IType> paramTypes, IType bodyType){
        this.paramTypes = paramTypes;
        this.bodyType = bodyType;
    }

    @Override
    public String getType() {
        return bodyType.getType();
    }

    @Override
    public String toString() {
        String result = "(";
        for (IType paramType : paramTypes){
            result += paramTypes.toString();
        }
        result += ")"+bodyType;
        return result;
    }


}
