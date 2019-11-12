package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.TypeErrorException;
import value.VBool;

public class ASTBooleanOp implements ASTNode {

    public static final String AND = "&&";
    public static final String OR = "||";
    private final ASTNode lhs;
    private final ASTNode rhs;
    private final String op;

    public ASTBooleanOp(String op, ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public IValue eval(Environment env) throws TypeErrorException {
        VBool v1 = VBool.check(lhs.eval(env));
        VBool v2 = VBool.check(rhs.eval(env));

        switch (op) {
            case AND:
                return VBool.and(v1, v2);
            case OR:
                return VBool.or(v1, v2);
            default:
                throw new UnexpectedOperatorException(op);
        }
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }

}
