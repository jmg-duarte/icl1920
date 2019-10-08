package ast;

import env.Environment;

public class ASTSub implements ASTNode {
    private ASTNode lhs;
    private ASTNode rhs;

    public ASTSub(ASTNode left, ASTNode right) {
        this.lhs = left;
        this.rhs = right;
    }

    public int eval(Environment env) {
        int o1 = lhs.eval(env);
        int o2 = rhs.eval(env);
        return o1 - o2;
    }
}
