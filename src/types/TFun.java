package types;

import ast.ASTNode;
import env.Environment;
import value.TypeErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TFun implements IType {

    private final List<IType> paramTypes;
    private final IType bodyType;

    public TFun(List<IType> paramTypes, IType bodyType) {
        this.paramTypes = paramTypes;
        this.bodyType = bodyType;
    }

    public IType getType() {
        return bodyType;
    }

    public List<IType> getParameters() {
        return paramTypes;
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
            final IType f1Type = this.getParameters().get(i);
            final IType f2Type = funParameters.get(i);
            if (!f1Type.equals(f2Type)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("fun (");
        for (IType paramType : paramTypes) {
            result.append(String.format("%s", paramType));
        }
        result.append(")").append(bodyType);
        return result.toString();
    }

    @Override
    public String getCompiledType() {
        final StringBuilder result = new StringBuilder("(");
        for (IType paramType : paramTypes) {
            result.append(String.format("%s", paramType.getCompiledType()));
        }
        result.append(")").append(bodyType);
        return result.toString();
    }

    public static TFun check(ASTNode node, Environment<IType> env) {
        IType expType = node.typecheck(env);
        if (!(expType instanceof TFun)) {
            throw new TypeErrorException(String.format("%s is not function type", node.toString()));
        }
        return ((TFun) expType);
    }
}
