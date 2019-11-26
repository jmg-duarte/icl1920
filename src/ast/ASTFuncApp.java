package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.VFunc;

import java.util.ArrayList;
import java.util.List;

public class ASTFuncApp implements ASTNode {
    private final ASTNode expr;
    private final List<ASTNode> args;

    public ASTFuncApp(ASTNode expr, List<ASTNode> args) {
        this.expr = expr;
        this.args = args;
    }

    @Override
    public IValue eval(Environment env) {
        VFunc func = VFunc.check(expr.eval(env));
        List<IValue> argVal = new ArrayList<>(args.size());
        for (ASTNode arg : args) {
            argVal.add(arg.eval(env));
        }
        return func.apply(argVal);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }
}
