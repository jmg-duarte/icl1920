package ast;

import compiler.*;
import env.Environment;
import types.IType;
import types.TRef;
import value.IValue;
import value.VRef;

public class ASTNew implements ASTNode {
    ASTNode num;
    IType referenceType;

    public ASTNew(ASTNode num){
        this.num = num;
        referenceType = null;
    }

    @Override
    public IValue eval(Environment env) {
        IValue value = num.eval(env);
        return new VRef(value);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();
        FrameStack frameStack = compiler.getfStack();
        Frame currentFrame = frameStack.newFrame();

        lb.appendLine("new " + currentFrame);
        lb.appendLine("dup");
        lb.appendLine("invokespecial " + currentFrame + "/<init>()V");
        lb.appendLine("dup");
        Assembler asm = num.compile(compiler,env);
        lb.appendLine("putfield " + currentFrame + "/v "+referenceType.getType()); //TODO change to type

       return new Assembler(lb.toString(),0);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        referenceType = num.typecheck(env);
        return new TRef(referenceType);
    }
}
