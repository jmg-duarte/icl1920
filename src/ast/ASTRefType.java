package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TRef;
import value.IValue;

public class ASTRefType implements ASTNode {
    ASTNode contentType;

    public ASTRefType(ASTNode type) {
        this.contentType = type;
    }

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
        return new TRef(contentType.typecheck(env));
    }
}
