package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TBool;
import types.TInt;
import value.IValue;

public class ASTPrint implements ASTNode {

    private final ASTNode exp;
    private IType type;

    public ASTPrint(ASTNode exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        System.out.println(exp.eval(env));
        return null;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        LineBuilder lb = new LineBuilder();
        Assembler printAsm = exp.compile(compiler, env);
        lb.appendLine("getstatic java/lang/System/out Ljava/io/PrintStream;");
        lb.append(printAsm);
        if (expType instanceof TInt|| expType instanceof TBool){
            lb.appendLine("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
        } else {
            lb.appendLine("invokestatic java/lang/String/valueOf(Ljava/lang/Object;)Ljava/lang/String;");
        }
        lb.appendLine("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        return new Assembler(lb.toString(), printAsm.getStack(), type);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        type = exp.typecheck(env);
        return type;
    }
}
