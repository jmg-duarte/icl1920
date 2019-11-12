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

    public IValue eval(Environment env) throws TypeErrorException {
        IValue o1 = lhs.eval(env);
        IValue o2 = rhs.eval(env);

        if (o1 instanceof VInt && o2 instanceof VInt) {
            switch (operator) {
                case Operator.ADD:
                    return new VInt(((VInt) o1).getValue() + ((VInt) o2).getValue());
                case Operator.MUL:
                    return new VInt(((VInt) o1).getValue() * ((VInt) o2).getValue());
                case Operator.DIV:
                    return new VInt(((VInt) o1).getValue() / ((VInt) o2).getValue());
                case Operator.SUB:
                    return new VInt(((VInt) o1).getValue() - ((VInt) o2).getValue());
                case Operator.REM:
                    return new VInt(((VInt) o1).getValue() % ((VInt) o2).getValue());
                default:
                    throw new IllegalStateException("unexpected operator: " + operator);
            }
        }
        throw new TypeErrorException("wrong arguments");
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