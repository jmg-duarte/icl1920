package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.TypeErrorException;
import value.VBool;
import value.VInt;

public class ASTComparisonOp implements ASTNode {
    ASTNode exp1;
    ASTNode exp2;
    String op;

    public ASTComparisonOp(String op, ASTNode exp1, ASTNode exp2){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(Environment env) throws TypeErrorException {
        VInt v1 = VInt.check(exp1.eval(env));
        VInt v2 = VInt.check(exp2.eval(env));

            switch (op) {
                case "==":
                    if (v1.getValue() == v2.getValue())
                        return new VBool(true);
                    else
                        return new VBool(false);
                case ">":
                    if(v1.getValue() > v2.getValue())
                        return new VBool(true);
                    else
                        return new VBool(false);
                case "<":
                    if(v1.getValue() < v2.getValue())
                        return new VBool(true);
                    else
                        return new VBool(false);
                case ">=":
                    if(v1.getValue() >= v2.getValue())
                        return new VBool(true);
                    else
                        return new VBool(false);
                case "<=":
                    if(v1.getValue() <= v2.getValue())
                        return new VBool(true);
                    else
                        return new VBool(false);
                case "!=":
                    if(v1.getValue() != v2.getValue())
                        return new VBool(true);
                    else
                        return new VBool(false);
                default:
                    throw new IllegalStateException("unexpected operator: " + op);
            }
        }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }
}
