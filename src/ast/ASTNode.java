package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;

public interface ASTNode {
    int eval(Environment env);

    Assembler compile(CoreCompiler compiler, Environment env);
}