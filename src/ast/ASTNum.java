package ast;

public class ASTNum implements ASTNode {
    int val;

    public ASTNum(int val){
        this.val = val;
    }

    public int eval() {
        return val;
    }
}
