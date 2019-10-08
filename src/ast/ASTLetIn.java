package ast;

import env.Environment;

public class ASTLetIn implements ASTNode {

    private String id;
    private ASTNode expression;
    private ASTNode body;

    public ASTLetIn(String id, ASTNode expression, ASTNode body) {
        this.id = id;
        this.expression = expression;
        this.body = body;
    }

    @Override
    public int eval(Environment env) {
        int expressionResult = expression.eval(env);
        Environment innerScope = (env == null) ? new Environment() : env.startScope();
        innerScope.associate(id, expressionResult);
        innerScope.endScope();
        return body.eval(innerScope);
    }
}
