package ast;

public class ASTMul implements ASTNode {
    private ASTNode lhs;
    private ASTNode rhs;

    public ASTMul(ASTNode left, ASTNode right){
        this.lhs = left;
        this.rhs = right;
    }

    public int eval(){
        int o1 = lhs.eval();
        int o2 = rhs.eval();
        return o1 * o2;
    }
}
