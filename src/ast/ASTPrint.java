package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import value.IValue;

public class ASTPrint implements ASTNode {
    ASTNode exp;

    public ASTPrint(ASTNode exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(Environment env) {
        System.out.println(exp.eval(env));
        return null;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();
        Assembler printAsm = exp.compile(compiler, env);
        lb.appendLine("getstatic java/lang/System/out Ljava/io/PrintStream;");
        lb.append(printAsm);
        lb.appendLine("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
        lb.appendLine("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        return new Assembler(lb.toString(), printAsm.getStack());
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return null;
    }
}
