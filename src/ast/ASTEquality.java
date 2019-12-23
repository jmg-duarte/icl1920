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
    public IValue eval(Environment<IValue> env) {
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
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Assembler leftAssembly = left.compile(compiler, env);
        Assembler rightAssembly = right.compile(compiler, env);

        String labelTrue = LabelMaker.getLabel();
        String labelFalse = LabelMaker.getLabel();

        LineBuilder lb = new LineBuilder();
        lb.append(leftAssembly, rightAssembly);
        lb.appendLine(Assembler.INTEGER_SUBTRACTION);

        switch (op) {
            case "==":
                lb.appendLine(Assembler.IF_EQUALS + " " + labelTrue);
                break;
            case "!=":
                lb.appendLine(Assembler.IF_NOT_EQUALS + " " + labelTrue);
                break;
            default:
                throw new IllegalStateException("unexpected operator: " + op);
        }

        lb.appendLine(Assembler.BOOLEAN_FALSE);
        lb.appendLine(Assembler.GO_TO + " " + labelFalse);
        lb.appendLine(labelTrue + ": ");
        lb.appendLine(Assembler.BOOLEAN_TRUE);
        lb.appendLine(labelFalse + ": ");

        return new Assembler(lb.toString(), leftAssembly.getStack() + rightAssembly.getStack(), TBool.TYPE);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        final IType o1 = left.typecheck(env);
        final IType o2 = right.typecheck(env);

        if (o1 instanceof TBool && o2 instanceof TBool || o1 instanceof TInt && o2 instanceof TInt) {
            return TBool.TYPE;
        } else {
            throw new TypeErrorException("expressions do not have the same type");
        }
    }

    @Override
    public IType getType() {
        return TBool.TYPE;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", left.toString(), op, right.toString());
    }
}
