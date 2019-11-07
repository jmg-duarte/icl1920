package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;

public class ASTId implements ASTNode {

    private String id;

    public ASTId(String id) {
        this.id = id;
    }

    @Override
    public int eval(Environment env) {
        return env.find(id);
    }



    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();
        lb.appendLine("aload_0");
        while (true) {
            Integer current = env.findInScope(id);
            int[] framePositions = env.findFrame(id); //TODO mudar aqui para ter o numero da frame e a posicao na frame
            if (current == null) {
                lb.append("getfield ",
                        env.getName(),
                        "/parent L",
                        env.endScope().getName(),
                        ";");
            } else {
                lb.append("getfield ",
                        env.getName(),
                        "/_ ",
                        id,
                        " I");
                break;
            }
        }

        return new Assembler(lb.toString(), 1);
    }
}
