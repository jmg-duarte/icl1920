package ast;

import env.Environment;

public class ASTNum implements ASTNode {
    private int val;

    public ASTNum(int val){
        this.val = val;
    }

    public int eval(Environment env) {
        return val;
    }
}
