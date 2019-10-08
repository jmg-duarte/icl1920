package ast;

import env.Environment;

public interface ASTNode{
    int eval(Environment env);
}