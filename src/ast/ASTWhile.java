package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
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
        return null;
    }
}
