public class ASTNum implements ASTNode {
    int val;

    ASTNum(int value){
        this.val = value;
    }

    public int eval() {
        return val;
    }
}
