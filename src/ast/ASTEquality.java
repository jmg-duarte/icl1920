package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.VBool;

public class ASTEquality implements ASTNode {

    private final String op;
    private final ASTNode left;
    private final ASTNode right;

    public ASTEquality(String op, ASTNode left, ASTNode right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public IValue eval(Environment env) {
        IValue v1 = left.eval(env);
        IValue v2 = right.eval(env);
        switch (op) {
            case "==":
                return new VBool(v1.equals(v2));
            case "!=":
                return new VBool(!v1.equals(v2));
            default:
                throw new IllegalStateException("unexpected operator: " + op);
        }
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }
}
