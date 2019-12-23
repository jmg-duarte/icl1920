package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TUndef;
import value.IValue;
import value.VUndef;

public class ASTPrint implements ASTNode {

    private final ASTNode exp;
    private IType type;

    public ASTPrint(ASTNode exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        System.out.println(exp.eval(env));
        return VUndef.UNDEFINED;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Assembler printAsm = exp.compile(compiler, env);
        return new Assembler(getPrintAsm(printAsm), printAsm.getStack(), type);
    }

    private String getPrintAsm(Assembler asm) {
        LineBuilder lb = new LineBuilder();
        lb.appendLine("getstatic java/lang/System/out Ljava/io/PrintStream;");
        lb.append(asm);
        lb.appendLine(String.format("invokestatic java/lang/String/valueOf(%s)Ljava/lang/String;", type.getCompiledType()));
        lb.appendLine("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        return lb.toString();
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        type = exp.typecheck(env);
        return TUndef.TYPE;
    }

    @Override
    public IType getType() {
        return TUndef.TYPE;
    }
}
