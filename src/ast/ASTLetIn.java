package ast;

import compiler.*;
import env.Environment;
import types.IType;
import value.IValue;
import value.TypeErrorException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ASTLetIn implements ASTNode {

    private Map<String, ASTNode> expressions; //map with all the associations -> inits
    private Map<String, ASTNode> expTypes; //map with all the associations -> types
    private ASTNode body;
    private Map<String, IType> types = new LinkedHashMap<>();
    private IType type;

    public ASTLetIn(Map<String, ASTNode> expressions, ASTNode body, Map<String, ASTNode> expTypes) {
        this.expressions = expressions;
        this.body = body;
        this.expTypes = expTypes;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws TypeErrorException {
        Environment<IValue> innerScope = env.startScope();
        for (Entry<String, ASTNode> e : expressions.entrySet()) {
            innerScope.associate(e.getKey(), e.getValue().eval(env));
        }
        innerScope.endScope();
        return body.eval(innerScope);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
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

        Environment<IType> compEnv = env.startScope(currentFrame.getFrameID());
        int counter = 0;
        for (String field : expressions.keySet()) {
            final ASTNode exp = expressions.get(field);
            final Assembler assembly = exp.compile(compiler, compEnv);
            final IType asmType =  types.get(field);

            compEnv.associate(field, asmType);
            currentFrame.addField(field, asmType);

            lb.appendLine("aload 4");
            lb.append(assembly);
            lb.appendLine("putfield " + currentFrame + "/_" + field + " " + asmType.getCompiledType());
        }

        lb.append(body.compile(compiler, compEnv));

        lb.appendLine("aload 4");
        lb.appendLine("getfield " + currentFrame + "/sl L" + oldFrame + ";");
        lb.appendLine("astore 4");
        return new Assembler(lb.toString(), 0, type);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        Environment<IType> innerScope = env.startScope();
        for (String id : expressions.keySet()) {
            IType declaredType = expTypes.get(id).typecheck(env);
            IType expression = expressions.get(id).typecheck(env);
            if (!declaredType.equals(expression)) {
                throw new TypeErrorException(String.format("type mismatch:\n\tdeclared type [%s]\n\texpression type: [%s]", declaredType.toString(), expression.toString()));
            }
            types.put(id, declaredType);
            innerScope.associate(id, declaredType);
        }
        innerScope.endScope();
        type = body.typecheck(innerScope);
        return type;
    }
}
