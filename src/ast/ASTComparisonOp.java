package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LabelMaker;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TBool;
import types.TInt;
import value.IValue;
import value.TypeErrorException;
import value.VBool;
import value.VInt;

public class ASTComparisonOp implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;
    private final String op;

    public ASTComparisonOp(String op, ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws TypeErrorException {
        final VInt v1 = VInt.check(lhs.eval(env));
        final VInt v2 = VInt.check(rhs.eval(env));
        switch (op) {
            case ">":
                return new VBool(v1.getValue() > v2.getValue());
            case "<":
                return new VBool(v1.getValue() < v2.getValue());
            case ">=":
                return new VBool(v1.getValue() >= v2.getValue());
            case "<=":
                return new VBool(v1.getValue() <= v2.getValue());
            default:
                throw new IllegalStateException("unexpected operator: " + op);
        }
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Assembler leftAssembly = lhs.compile(compiler, env);
        Assembler rightAssembly = rhs.compile(compiler, env);

        String labelTrue = LabelMaker.getLabel();
        String labelFalse = LabelMaker.getLabel();

        LineBuilder lb = new LineBuilder();
        lb.append(leftAssembly, rightAssembly);
        lb.appendLine(Assembler.INTEGER_SUBTRACTION);

        switch (op) {
            case ">":
                lb.appendLine(Assembler.IF_GREATER + " " + labelTrue);
                break;
            case "<":
                lb.appendLine(Assembler.IF_LESS + " " + labelTrue);
                break;
            case ">=":
                lb.appendLine(Assembler.IF_GREATER_EQ + " " + labelTrue);
                break;
            case "<=":
                lb.appendLine(Assembler.IF_LESS_EQ + " " + labelTrue);
                break;
            default:
                throw new IllegalStateException("unexpected operator: " + op);
        }

        lb.appendLine(Assembler.BOOLEAN_FALSE);
        lb.appendLine(Assembler.GO_TO + " " + labelFalse);
        lb.appendLine(labelTrue + ":\n" + Assembler.BOOLEAN_TRUE);
        lb.appendLine(labelFalse + ": ");
        return new Assembler(lb.toString(), leftAssembly.getStack() + rightAssembly.getStack(), TBool.TYPE);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType leftType = lhs.typecheck(env);
        if (!(leftType instanceof TInt)) {
            throw new TypeErrorException(String.format("left hand (%s) expression must be of type int, got type %s", lhs.toString(), leftType.toString()));
        }
        IType rightType = rhs.typecheck(env);
        if (!(rightType instanceof TInt)) {
            throw new TypeErrorException(String.format("right hand (%s) expression must be of type int, got type %s", rhs.toString(), rightType.toString()));
        }
        return TBool.TYPE;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", lhs.toString(), op, rhs.toString());
    }
}
