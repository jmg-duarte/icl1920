package ast;

import compiler.Compiler;
import env.Environment;

public class ASTNum implements ASTNode {
    private int val;

    public ASTNum(int val){
        this.val = val;
    }

    @Override
    public int eval(Environment env) {
        return val;
    }

    @Override
    public void compile(Compiler compiler, Environment env) {
        String code = "sipush " + val;
    }
}
