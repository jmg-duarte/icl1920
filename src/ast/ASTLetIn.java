package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
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
    public Assembler compile(CoreCompiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();

        String currentFrame = compiler.newFrame();

        lb.append("new " + currentFrame);
        lb.append("dup");
        lb.append("invokespecial " + currentFrame + "/<init>()V");
        lb.append("dup");
        lb.append("aload 4");
        lb.append("putfield " + currentFrame + "/sl L" + compiler.oldFrame());

        return new Assembler(lb.toString(), 0);
    }
}
