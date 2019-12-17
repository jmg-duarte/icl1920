package ast.types;

import ast.ASTNode;
import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import value.IValue;

public abstract class ASTType implements ASTNode {

    @Override
    public IValue eval(Environment<IValue> env) {
        throw new RuntimeException("eval called on type");
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        throw new RuntimeException("compile called on type");
    }
}
