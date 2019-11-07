package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.Frame;
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
        Frame oldFrame = compiler.getOldFrame();
        Frame currentFrame = compiler.newFrame();

        lb.appendLine("new " + currentFrame);
        lb.appendLine("dup");
        lb.appendLine("invokespecial " + currentFrame + "/<init>()V");
        lb.appendLine("dup");
        lb.appendLine("aload 4");
        lb.appendLine("putfield " + currentFrame + "/sl L" + oldFrame + ";");
        lb.appendLine("astore 4");

        Environment compEnv = env.startScope(currentFrame.getFrameID());
        int counter = 0;
        for (String field : expressions.keySet()) {
            compEnv.associate(field, counter++);
            currentFrame.addField(field);
            lb.appendLine("aload 4");
            lb.append(expressions.get(field).compile(compiler, compEnv));
            lb.appendLine("putfield " + currentFrame + "/" + field + " I");
        }

        lb.append(body.compile(compiler, compEnv));

        lb.appendLine("aload 4");
        lb.appendLine("getfield " + currentFrame + "/sl L" + oldFrame + ";");
        lb.appendLine("astore 4");
        return new Assembler(lb.toString(), 0);
    }
}
