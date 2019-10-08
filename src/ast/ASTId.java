package ast;

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
}
