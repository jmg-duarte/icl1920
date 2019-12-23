package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import compiler.Reference;
import env.Environment;
import types.IType;
import types.TRef;
import value.IValue;
import value.VRef;

public class ASTNew implements ASTNode {
    private final ASTNode expression;
    private TRef reference;

    public ASTNew(ASTNode expression) {
        this.expression = expression;
    }

    private static String getNewAsm(Assembler asm, Reference ref, IType refInnerType) {
        final String r = TRef.getReferenceClass(refInnerType);
        LineBuilder lb = new LineBuilder();
        lb.appendLine("new " + ref.getReferenceID());
        lb.appendLine("dup");
        lb.appendLine(String.format("invokespecial %s/<init>()V", r));
        lb.appendLine("dup");
        lb.append(asm);
        lb.appendLine(String.format("putfield %s/v %s", r, refInnerType.getCompiledType()));
        return lb.toString();
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        IValue value = expression.eval(env);
        return new VRef(value);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        final Assembler asm = expression.compile(compiler, env);
        final IType refInnerType = reference.getInnerType();
        final Reference ref = compiler.newReference(reference.getInnerType());
        return new Assembler(getNewAsm(asm, ref, refInnerType), asm.getStack(), reference);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        reference = new TRef(expression.typecheck(env));
        return reference;
    }

    @Override
    public IType getType() {
        return reference;
    }
}
