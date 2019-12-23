package ast.types;

import env.Environment;
import types.IType;
import types.TBool;

public class ASTBoolType extends ASTType {

    @Override
    public IType typecheck(Environment<IType> env) {
        return TBool.TYPE;
    }

    @Override
    public IType getType() {
        return TBool.TYPE;
    }
}
