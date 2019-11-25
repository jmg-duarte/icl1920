package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LabelMaker;
import compiler.LineBuilder;
import env.Environment;
import value.IValue;
import value.VBool;

public class ASTWhile implements ASTNode {
    private final ASTNode conditional;
    private final ASTNode body;

    public ASTWhile(ASTNode conditional, ASTNode body) {
        this.conditional = conditional;
        this.body = body;
    }

    @Override
    public IValue eval(Environment env) {
        Environment loopEnv = env.startScope();
        VBool b = VBool.check(conditional.eval(loopEnv));
        IValue res = b;
        while (b.getValue()) {
            res = body.eval(loopEnv);
            b = VBool.check(conditional.eval(loopEnv));
        }
        return res;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
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
                bodyAssembly.getStack());
    }
}
