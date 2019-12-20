package types;

import ast.ASTNode;
import env.Environment;
import value.TypeErrorException;

import java.util.List;

public class TFun implements IType {

    private final List<IType> paramTypes;
    private final IType bodyType;
    private String name;

    public TFun(List<IType> paramTypes, IType bodyType) {
        this.paramTypes = paramTypes;
        this.bodyType = bodyType;
    }

    public TFun(TFun tFun, String name) {
        this(tFun.paramTypes, tFun.bodyType);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TFun check(ASTNode node, Environment<IType> env) {
        IType expType = node.typecheck(env);
        if (!(expType instanceof TFun)) {
            throw new TypeErrorException(String.format("%s is not function type", node.toString()));
        }
        return ((TFun) expType);
    }

    public IType getType() {
        return bodyType;
    }

    public List<IType> getParameters() {
        return paramTypes;
    }

    public String getCallType() {
        final StringBuilder result = new StringBuilder("(");
        for (IType paramType : paramTypes) {
            result.append(String.format("%s", paramType.getCompiledType()));
        }
        result.append(")").append(bodyType.getCompiledType());
        return result.toString();
    }

    @Override
    public String getCompiledType() {
        return String.format("L%s;", this.name);
    }

    @Override
    public String getClosureType() {
        final StringBuilder res = new StringBuilder();
        for (IType paramType : paramTypes) {
            res.append(paramType.getClosureType());
            res.append("x");
        }
        res.append(bodyType.getClosureType());
        return res.toString();
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
}
