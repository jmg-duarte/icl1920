package types;

import java.util.Iterator;
import java.util.List;

public class TFun implements IType {

    public List<IType> paramTypes;
    public IType bodyType;

    public TFun(List<IType> paramTypes, IType bodyType){
        this.paramTypes = paramTypes;
        this.bodyType = bodyType;
    }

    public IType getType() {return bodyType; }

    @Override
    public String toString() {
        String result = "(";
        for (IType paramType : paramTypes){
            result += paramTypes.toString();
        }
        result += ")"+bodyType;
        return result;
    }

    public List<IType> getParameters(){
        return paramTypes;
    }

    @Override
    public boolean equals(Object object){
        if(!( object instanceof TFun )){
            return false;
        }
        if(!(this.getType().equals(((TFun) object).getType()))) {
            return false;
        }
        if(!(paramTypes.size() == ((TFun)object).getParameters().size())) {
            return false;
        }
        Iterator<IType> typesIt = ((TFun)object).getParameters().iterator();
        for(IType paramType : paramTypes) {
            if(!paramType.equals(typesIt.next())){
                return false;
            }
        }
        return true;
    }
}
