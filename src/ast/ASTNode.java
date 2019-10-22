package ast;

import compiler.Assembler;
import compiler.Compiler;
import env.Environment;

public interface ASTNode {
    int eval(Environment env);

    Assembler compile(Compiler compiler, Environment env);
}