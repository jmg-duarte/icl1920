package ast;

import compiler.Assembler;
import compiler.Compiler;
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
    public Assembler compile(Compiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();
        lb.appendLine("aload_0");

        while (true) {
            Integer current = env.findInScope(id);
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
