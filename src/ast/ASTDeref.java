package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import value.IValue;
import value.VRef;

public class ASTDeref implements ASTNode {

    private final ASTNode expression;

    public ASTDeref(ASTNode expression) {
        this.expression = expression;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public IValue eval(Environment env) {
        return VRef.check(expression.eval(env)).get();
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return null;
    }
}
