package ast;

public class ASTDiv implements ASTNode {
    ASTNode lhs;
    ASTNode rhs;

    public ASTDiv(ASTNode left, ASTNode right){
        this.lhs = left;
        this.rhs = right;
    }

    public int eval(){
        int o1 = lhs.eval();
        int o2 = rhs.eval();
        return o1 / o2;
    }
}
