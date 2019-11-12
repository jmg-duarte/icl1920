package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;

public interface ASTNode {
    IValue eval(Environment env);

    Assembler compile(CoreCompiler compiler, Environment env);
}