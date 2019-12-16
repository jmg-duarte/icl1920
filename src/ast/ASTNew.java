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
    ASTNode num;
    IType referenceType;

    public ASTNew(ASTNode num) {
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
        Assembler asm = num.compile(compiler, env);

        if (referenceType instanceof TBool || referenceType instanceof TInt) {
        Reference ref = compiler.newReference("ref_int");
            lb.appendLine("new "+ref.getReferenceID());
            lb.appendLine("dup");
            lb.appendLine("invokespecial "+ref.getReferenceID()+"/<init>()V");
            lb.appendLine("dup");
            lb.append(asm);
            lb.appendLine("putfield "+ref.getReferenceID()+"/v " + referenceType.toString());
        }
        if (referenceType instanceof TRef) {
            Reference ref = compiler.newReference("ref_class");
            lb.appendLine("new ref_class");
            lb.appendLine("dup");
            lb.appendLine("invokespecial ref_class/<init>()V");
            lb.appendLine("dup");
            lb.append(asm);
            lb.appendLine("putfield ref_class/v " + referenceType.toString());
        }
        return new Assembler(lb.toString(), asm.getStack());
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        referenceType = num.typecheck(env);
        return new TRef(referenceType);
    }
}
