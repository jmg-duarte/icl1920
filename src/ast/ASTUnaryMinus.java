package ast;

import env.Environment;

public class ASTUnaryMinus implements ASTNode{

    private ASTNode expr;

    public ASTUnaryMinus(ASTNode expr) {
        this.expr = expr;
    }


    @Override
    public int eval(Environment env) {
        return - this.expr.eval(env);
    }
}
