package ast.types;

import ast.ASTNode;
import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TBool;
import value.IValue;

public class ASTBoolType extends ASTType {

    @Override
    public IType typecheck(Environment<IType> env) {
        return new TBool();
    }
}
