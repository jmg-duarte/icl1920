package ast;

public class ASTUnaryMinus implements ASTNode{

    private ASTNode expr;

    public ASTUnaryMinus(ASTNode expr) {
        this.expr = expr;
    }


    @Override
    public int eval() {
        return - this.expr.eval();
    }
}
