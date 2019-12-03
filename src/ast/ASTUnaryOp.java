package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import value.IValue;
import value.TypeErrorException;
import value.VInt;

public class ASTUnaryOp implements ASTNode {

    private final String operator;
    private final ASTNode expr;

    public ASTUnaryOp(String operator, ASTNode expr) {
        this.operator = operator;
        this.expr = expr;
    }

    @Override
    public IValue eval(Environment env) throws TypeErrorException {
        VInt v = VInt.check(expr.eval(env));

        switch (operator) {
            case Operator.ADD:
                break;
            case Operator.SUB:
                v = VInt.negate(v);
        }
        return v;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        Assembler asm = expr.compile(compiler, env);
        LineBuilder lb = new LineBuilder();
        lb.append(asm);

        switch (operator) {
            case Operator.ADD:
                break;
            case Operator.SUB:
                lb.appendLine("sipush -1");
                lb.appendLine(Assembler.INTEGER_MULTIPLICATION);
                break;
            default:
                throw new IllegalStateException("unexpected operator: " + operator);
        }
        return new Assembler(lb.toString(), asm.getStack());
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return null;
    }
}