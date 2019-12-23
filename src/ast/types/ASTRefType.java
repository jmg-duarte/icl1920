package ast.types;

import ast.ASTNode;
import env.Environment;
import types.IType;
import types.TRef;

public class ASTRefType extends ASTType {
    private final ASTNode contentType;
    private TRef type;

    public ASTRefType(ASTNode type) {
        this.contentType = type;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        type = new TRef(contentType.typecheck(env));
        return type;
    }

    @Override
    public IType getType() {
        return type;
    }
}
