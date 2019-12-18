package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import compiler.Reference;
import env.Environment;
import types.IType;
import types.TBool;
import types.TInt;
import types.TRef;
import value.IValue;
import value.VRef;

public class ASTNew implements ASTNode {
    private final ASTNode expression;
    private TRef reference;

    public ASTNew(ASTNode expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        IValue value = expression.eval(env);
        return new VRef(value);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        LineBuilder lb = new LineBuilder();
        Assembler asm = expression.compile(compiler, env);
        IType refInnerType = reference.getInnerType();
        if (refInnerType instanceof TBool || refInnerType instanceof TInt) {
            lb.appendLine("new ref_int");
            lb.appendLine("dup");
            lb.appendLine("invokespecial "+ref.getReferenceID()+"/<init>()V");
            lb.appendLine("dup");
            lb.append(asm);
            lb.appendLine("putfield ref_int/v " + refInnerType.toString());
        }
        if (refInnerType instanceof TRef) {
            lb.appendLine("new ref_class");
            lb.appendLine("dup");
            lb.appendLine("invokespecial ref_class/<init>()V");
            lb.appendLine("dup");
            lb.append(asm);
            lb.appendLine("putfield ref_class/v " + reference.toString());
        }
        return new Assembler(lb.toString(), asm.getStack(), reference);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        reference = new TRef(expression.typecheck(env));
        return reference;
    }
}
