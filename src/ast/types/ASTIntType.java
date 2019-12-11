package ast.types;

import ast.ASTNode;
import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TInt;
import value.IValue;

public class ASTIntType extends ASTType {

    @Override
    public IType typecheck(Environment<IType> env) {
        return new TInt();
    }
}
