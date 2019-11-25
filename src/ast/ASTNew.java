package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import value.IValue;
import value.VInt;
import value.VRef;

public class ASTNew implements ASTNode {
    ASTNode num;

    public ASTNew(ASTNode num){
        this.num = num;
    }

    @Override
    public IValue eval(Environment env) {
        IValue value = num.eval(env);
        return new VRef(value);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
       /* LineBuilder lb = new LineBuilder();

        Assembler asm = num.compile(compiler,env);


        lb.appendLine("new "+asm);
        lb.appendLine("invokespecial " + currentFrame + "/<init>()V");
        lb.appendLine("dup");
        lb.appendLine("putfield " + currentFrame + "/v I");*/
       return null;
    }
}
