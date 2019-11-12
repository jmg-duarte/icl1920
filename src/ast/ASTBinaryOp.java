package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import value.IValue;
import value.TypeErrorException;
import value.VInt;

public class ASTBinaryOp implements ASTNode {
    private final String operator;
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTBinaryOp(String operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.lhs = left;
        this.rhs = right;
    }

    public IValue eval(Environment env) {
        VInt o1 = VInt.check(lhs.eval(env));
        VInt o2 = VInt.check(rhs.eval(env));

        switch (operator) {
            case Operator.ADD:
                return VInt.add(o1, o2);
            case Operator.MUL:
                return VInt.mul(o1, o2);
            case Operator.DIV:
                return VInt.div(o1, o2);
            case Operator.SUB:
                return VInt.sub(o1, o2);
            case Operator.REM:
                return VInt.mod(o1, o2);
            default:
                throw new IllegalStateException("unexpected operator: " + operator);
        }
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        Assembler leftAssembly = lhs.compile(compiler, env);
        Assembler rightAssembly = rhs.compile(compiler, env);

        LineBuilder lb = new LineBuilder();
        lb.append(leftAssembly, rightAssembly);

        switch (operator) {
            case Operator.ADD:
                lb.appendLine(Assembler.INTEGER_ADDITION);
                break;
            case Operator.MUL:
                lb.appendLine(Assembler.INTEGER_MULTIPLICATION);
                break;
            case Operator.DIV:
                lb.appendLine(Assembler.INTEGER_DIVISION);
                break;
            case Operator.SUB:
                lb.appendLine(Assembler.INTEGER_SUBTRACTION);
                break;
            case Operator.REM:
                lb.appendLine(Assembler.INTEGER_MODULUS);
                break;
            default:
                throw new IllegalStateException("unexpected operator: " + operator);
        }

        return new Assembler(lb.toString(), leftAssembly.getStack() + rightAssembly.getStack());
    }
}