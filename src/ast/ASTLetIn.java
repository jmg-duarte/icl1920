package ast;

import compiler.*;
import env.Environment;
import value.IValue;
import value.TypeErrorException;
import value.VInt;

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
    public IValue eval(Environment env) throws TypeErrorException {
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
        FrameStack frameStack = compiler.getfStack();
        Frame oldFrame = frameStack.getOldFrame();
        Frame currentFrame = frameStack.newFrame();

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
            final ASTNode exp = expressions.get(field);
            final Assembler assembly = exp.compile(compiler, compEnv);

            compEnv.associate(field, new VInt(counter++));
            currentFrame.addField(field);

            lb.appendLine("aload 4");
            lb.append(assembly);
            lb.appendLine("putfield " + currentFrame + "/_" + field + " I");
        }

        lb.append(body.compile(compiler, compEnv));

        lb.appendLine("aload 4");
        lb.appendLine("getfield " + currentFrame + "/sl L" + oldFrame + ";");
        lb.appendLine("astore 4");
        return new Assembler(lb.toString(), 0);
    }
}
