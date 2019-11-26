package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import value.IValue;

public class ASTId implements ASTNode {

    private String id;

    public ASTId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public IValue eval(Environment env) {
        return env.find(id);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();
        lb.appendLine("aload 4");
        while (true) {
            IValue current = env.findInScope(id);
            // int[] framePositions = env.findFrame(id); //TODO mudar aqui para ter o numero da frame e a posicao na frame
            if (current == null) {
                lb.appendLine("getfield " +
                        env.getName() +
                        "/sl L" +
                        env.endScope().getName() + ";");
                env = env.endScope();
            } else {
                lb.appendLine("getfield " +
                        env.getName() +
                        "/_" +
                        id +
                        " I");
                break;
            }
        }

        return new Assembler(lb.toString(), 1);
    }
}
