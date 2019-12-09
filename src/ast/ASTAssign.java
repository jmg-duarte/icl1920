package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TRef;
import value.IValue;
import value.TypeErrorException;
import value.VRef;

public class ASTAssign implements ASTNode {

    private final ASTNode left;
    private final ASTNode right;
    private final IType contentType;

    public ASTAssign(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
        this.contentType = null;
    }

    @Override
    public IValue eval(Environment env) {
        VRef ref = VRef.check(left.eval(env));
        ref.set(right.eval(env));
        return ref;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();
        lb.appendLine("checkcast");
        lb.appendLine("putfield");
        //TODO ver a parte da stack
        return new Assembler(lb.toString(),0);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType leftType = left.typecheck(env);
        if (!(leftType instanceof TRef)){
            throw new TypeErrorException();
        }
        IType rightType = right.typecheck(env);
        //TODO aceitar tudo o que esta do lado direito?
        return leftType;
    }
}
