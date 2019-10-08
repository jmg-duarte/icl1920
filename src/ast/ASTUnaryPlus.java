package ast;


import env.Environment;

public class ASTUnaryPlus implements ASTNode{

    private ASTNode expr;

    public ASTUnaryPlus(ASTNode expr) {
        this.expr = expr;
    }


    @Override
    public int eval(Environment env) {
        return this.expr.eval(env);
    }
}

