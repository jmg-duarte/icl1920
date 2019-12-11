package types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TFun implements IType {

    private final Map<String, IType> paramTypes;
    private final IType bodyType;

    public TFun(Map<String, IType> paramTypes, IType bodyType) {
        this.paramTypes = paramTypes;
        this.bodyType = bodyType;
    }

    public IType getType() {
        return bodyType;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("(");
        for (Map.Entry<String, IType> paramType : paramTypes.entrySet()) {
            final String s = String.format("%s:%s", paramType.getKey(), paramType.getValue().toString());
            result.append(s);
        }
        result.append(")").append(bodyType);
        return result.toString();
    }

    public List<IType> getParameters() {
        return new ArrayList<>(paramTypes.values());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TFun)) {
            return false;
        }
        final TFun fun = (TFun) object;
        if (!(this.getType().equals(fun.getType()))) {
            return false;
        }
        final List<IType> funParameters = fun.getParameters();
        if (!(paramTypes.size() == funParameters.size())) {
            return false;
        }
        for (int i = 0; i < this.getParameters().size(); i++) {
            if (!this.getParameters().get(i).equals(funParameters.get(i))) {
                return false;
            }
        }
        return true;
    }
}
