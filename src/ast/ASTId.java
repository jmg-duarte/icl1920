package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import value.IValue;

public class ASTId implements ASTNode {

    private final String id;
    private IType type;

    public ASTId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        return env.find(id);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        LineBuilder lb = new LineBuilder();
        lb.appendLine("aload 4");
        while (true) {
            IType current = env.findInScope(id);
            // int[] framePositions = env.findFrame(id); //TODO mudar aqui para ter o numero da frame e a posicao na frame
            if (current == null) {
                final String currEnvName = env.getName();
                final Environment<IType> parentEnv = env.endScope();
                final String parentEnvName = parentEnv.getName();
                lb.appendLine(String.format("getfield %s/sl L%s;", currEnvName, parentEnvName));
                env = env.endScope();
            } else {
                lb.appendLine(String.format("getfield %s/_%s %s", env.getName(), id, current.getCompiledType()));
                break;
            }
        }
        return new Assembler(lb.toString(), 1, type);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        type = env.find(id);
        return type;
    }

    @Override
    public String toString() {
        return id;
    }
}
