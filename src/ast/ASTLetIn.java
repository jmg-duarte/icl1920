package ast;

import compiler.Compiler;
import env.Environment;

import java.util.Map;
import java.util.Map.Entry;

public class ASTLetIn implements ASTNode {

    // private String id;
    //private ASTNode expression;
    private Map<String, ASTNode> expressions; //map with all the associations
    private ASTNode body;

    public ASTLetIn(Map<String, ASTNode> expressions, ASTNode body) {
        //this.id = id;
        this.expressions = expressions;
        this.body = body;
    }

    @Override
    public int eval(Environment env) {
        Environment innerScope = env.startScope();
        for (Entry<String, ASTNode> e : expressions.entrySet()) {
            innerScope.associate(e.getKey(), e.getValue().eval(env));
        }
        innerScope.endScope();
        return body.eval(innerScope);
    }

    @Override
    public void compile(Compiler compiler, Environment env) {

    }
}
