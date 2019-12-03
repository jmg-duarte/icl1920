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
    private final ASTNode exp1;
    private final ASTNode exp2;
    private final String op;

    public ASTComparisonOp(String op, ASTNode exp1, ASTNode exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(Environment env) throws TypeErrorException {
        final VInt v1 = VInt.check(exp1.eval(env));
        final VInt v2 = VInt.check(exp2.eval(env));
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
    public Assembler compile(CoreCompiler compiler, Environment env) {
        Assembler leftAssembly = exp1.compile(compiler, env);
        Assembler rightAssembly = exp2.compile(compiler, env);

        String labelTrue = LabelMaker.getLabel();
        String labelFalse = LabelMaker.getLabel();

        LineBuilder lb = new LineBuilder();
        lb.append(leftAssembly, rightAssembly);
        lb.appendLine(Assembler.INTEGER_SUBTRACTION);

        switch(op){
            case ">":
                lb.appendLine(Assembler.IF_GREATER + " " + labelTrue);
                break;
            case "<":
                lb.appendLine(Assembler.IF_LESS+ " " + labelTrue);
                break;
            case ">=":
                lb.appendLine(Assembler.IF_GREATER_EQ+ " " + labelTrue);
                break;
            case "<=":
                lb.appendLine(Assembler.IF_LESS_EQ+ " " + labelTrue);
                break;
            default:
                throw new IllegalStateException("unexpected operator: " + op);
        }

        lb.appendLine(Assembler.BOOLEAN_FALSE);
        lb.appendLine(Assembler.GO_TO + " " + labelFalse);
        lb.appendLine(labelTrue + ": " + Assembler.BOOLEAN_TRUE);
        lb.appendLine(labelFalse + ": ");
        return new Assembler(lb.toString(), leftAssembly.getStack() + rightAssembly.getStack());
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType o1 = exp1.typecheck(env);
        IType o2 = exp2.typecheck(env);

        if(o1 instanceof TInt && o2 instanceof TInt || o1 instanceof TBool && o2 instanceof TBool){
            return new TBool();
        } else {
            throw new TypeErrorException(); //TODO confirmar
        }
    }

}
