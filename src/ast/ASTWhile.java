package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LabelMaker;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TBool;
import value.IValue;
import value.TypeErrorException;
import value.VBool;

public class ASTWhile implements ASTNode {
    private final ASTNode conditional;
    private final ASTNode body;

    public ASTWhile(ASTNode conditional, ASTNode body) {
        this.conditional = conditional;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        Environment<IValue> loopEnv = env.startScope();
        VBool b = VBool.check(conditional.eval(loopEnv));
        IValue res = b;
        while (b.getValue()) {
            res = body.eval(loopEnv);
            b = VBool.check(conditional.eval(loopEnv));
        }
        return res;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Assembler condAssembly = conditional.compile(compiler, env);
        Assembler bodyAssembly = body.compile(compiler, env);

        String labelTrue = LabelMaker.getLabel();
        String labelFalse = LabelMaker.getLabel();

        LineBuilder lb = new LineBuilder();
        lb.appendLine(labelTrue + ":");
        lb.append(condAssembly);
        lb.appendLine(Assembler.IF_EQUALS + " " + labelFalse);
        lb.append(bodyAssembly);
        lb.appendLine(Assembler.GO_TO + " " + labelTrue);
        lb.appendLine(labelFalse + ":");

        return new Assembler(lb.toString(), condAssembly.getStack() +
                bodyAssembly.getStack(), TBool.TYPE);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType conditionalType = conditional.typecheck(env);
        if (!(conditionalType instanceof TBool)) {
            throw new TypeErrorException("while condition must be of type bool");
        }
        body.typecheck(env);
        return TBool.TYPE;
    }
}
