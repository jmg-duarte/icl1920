package ast.types;

import ast.ASTNode;
import env.Environment;
import types.IType;
import types.TFun;

import java.util.LinkedList;
import java.util.List;

public class ASTFunType extends ASTType {
    private List<ASTNode> paramTypes;
    private ASTNode bodyType;
    private TFun type;

    public ASTFunType(List<ASTNode> paramTypes, ASTNode bodyType) {
        this.paramTypes = paramTypes;
        this.bodyType = bodyType;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        List<IType> types = new LinkedList<>();
        for (ASTNode param : paramTypes) {
            types.add(param.typecheck(env));
        }
        type = new TFun(types, bodyType.typecheck(env));
        return type;
    }

    @Override
    public IType getType() {
        return type;
    }
}
