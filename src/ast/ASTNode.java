package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import value.IValue;

public interface ASTNode {
    IValue eval(Environment<IValue> env);

    Assembler compile(CoreCompiler compiler, Environment<IValue> env);

    IType typecheck(Environment<IType> env);
}