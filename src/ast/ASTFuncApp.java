package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TFun;
import value.IValue;
import value.TypeErrorException;
import value.VFunc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ASTFuncApp implements ASTNode {
    private final ASTNode expr;
    private final List<ASTNode> args;

    public ASTFuncApp(ASTNode expr, List<ASTNode> args) {
        this.expr = expr;
        this.args = args;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        VFunc func = VFunc.check(expr.eval(env));
        List<IValue> argVal = new ArrayList<>(args.size());
        for (ASTNode arg : args) {
            argVal.add(arg.eval(env));
        }
        return func.apply(argVal);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        TFun expType = TFun.check(expr, env);
        List<IType> params = expType.getParameters();
        if (params.size() != args.size()) {
            throw new TypeErrorException("Incorrect number of arguments");
        }

        Iterator<IType> itParameters = params.iterator();
        Iterator<ASTNode> itArguments = args.iterator();

        while (itParameters.hasNext()) {
            ASTNode currArg = itArguments.next();
            IType currParam = itParameters.next();
            if (!(currArg.typecheck(env).equals(currParam))) {
                throw new TypeErrorException("Argument type does not match w/ param type");
            }
        }

        return expType.getType();
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", expr.toString(), args.toString());
    }
}

