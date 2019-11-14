package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.VRef;

public class ASTAssign implements ASTNode {

    private final ASTNode left;
    private final ASTNode right;

    public ASTAssign(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue eval(Environment env) {
        VRef ref = VRef.check(left.eval(env));
        ref.set(right.eval(env));
        return ref;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }
}
