package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
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
    public Assembler compile(CoreCompiler compiler, Environment env) {
        String code = "sipush " + val + "\n";
        return new Assembler(code, 1);
    }
}
