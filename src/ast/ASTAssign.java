package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TBool;
import types.TInt;
import types.TRef;
import value.IValue;
import value.TypeErrorException;
import value.VRef;

public class ASTAssign implements ASTNode {

    private final ASTNode left;
    private final ASTNode right;
    private IType contentType;

    public ASTAssign(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
        this.contentType = null;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        VRef ref = VRef.check(left.eval(env));
        ref.set(right.eval(env));
        return ref;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();
        Assembler leftAsm = left.compile(compiler, env);
        Assembler rightAsm = right.compile(compiler,env);
        if (contentType instanceof TBool || contentType instanceof TInt) {
            lb.append(leftAsm);
            lb.appendLine("checkcast ref_int");
            lb.append(rightAsm);
            lb.appendLine("putfield ref_int/v " + contentType.toString());
        }
        if (contentType instanceof TRef) {
            lb.append(leftAsm);
            lb.appendLine("checkcast ref_class");
            lb.append(rightAsm);
            right.compile(compiler, env);
            lb.appendLine("putfield ref_class/v " + contentType.toString());
        }
        return new Assembler(lb.toString(),leftAsm.getStack()+rightAsm.getStack());
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType leftType = left.typecheck(env);
        if (!(leftType instanceof TRef)){
            throw new TypeErrorException();
        }
        contentType = right.typecheck(env);
        if(!(contentType.equals(leftType.getType()))){
            throw new TypeErrorException(); //wrong type
        }
        return leftType;
    }

}
