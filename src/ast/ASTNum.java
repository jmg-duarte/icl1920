package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.VInt;

public class ASTNum implements ASTNode {
    private int val;

    public ASTNum(int val){
        this.val = val;
    }

    @Override
    public IValue eval(Environment env) {
        return new VInt(val);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        String code = "sipush " + val;
        return new Assembler(code, 1);
    }
}
