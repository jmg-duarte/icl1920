package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TBool;
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
    public IValue eval(Environment<IValue> env) throws TypeErrorException {
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
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Assembler leftAssembly = lhs.compile(compiler, env);
        Assembler rightAssembly = rhs.compile(compiler, env);

        LineBuilder lb = new LineBuilder();
        lb.append(leftAssembly, rightAssembly);

        switch (op) {
            case AND:
                lb.appendLine(Assembler.BOOLEAN_AND);
                break;
            case OR:
                lb.appendLine(Assembler.BOOLEAN_OR);
                break;
            default:
                throw new IllegalStateException("unexpected operator: " + op);
        }

        return new Assembler(lb.toString(), leftAssembly.getStack() + rightAssembly.getStack(), TBool.TYPE);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType o1 = lhs.typecheck(env);
        IType o2 = rhs.typecheck(env);
        if (o1 instanceof TBool && o2 instanceof TBool) {
            return TBool.TYPE;
        } else {
            throw new TypeErrorException("both values must be of type bool");
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", lhs.toString(), op, rhs.toString());
    }
}
