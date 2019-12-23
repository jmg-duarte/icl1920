package ast.types;

import env.Environment;
import types.IType;
import types.TInt;

public class ASTIntType extends ASTType {

    @Override
    public IType typecheck(Environment<IType> env) {
        return TInt.TYPE;
    }

    @Override
    public IType getType() {
        return TInt.TYPE;
    }
}
