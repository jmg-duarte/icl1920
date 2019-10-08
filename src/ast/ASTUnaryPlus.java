package ast;


public class ASTUnaryPlus implements ASTNode{

    private ASTNode expr;

    public ASTUnaryPlus(ASTNode expr) {
        this.expr = expr;
    }


    @Override
    public int eval() {
        return this.expr.eval();
    }
}

