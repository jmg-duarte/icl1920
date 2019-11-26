package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.VFunc;

import java.util.List;

public class ASTFunc implements ASTNode {
    private final List<ASTId> parameters;
    private final ASTNode body;

    public ASTFunc(List<ASTId> parameters, ASTNode body) {
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public IValue eval(Environment env) {
        return new VFunc(parameters, body, env);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }
}
