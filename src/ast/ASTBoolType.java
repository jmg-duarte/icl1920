package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TBool;
import value.IValue;

public class ASTBoolType implements ASTNode {

    @Override
    public IValue eval(Environment<IValue> env) {
        return null;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IValue> env) {
        return null;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return new TBool();
    }
}
