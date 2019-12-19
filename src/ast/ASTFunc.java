package ast;

import compiler.*;
import env.Environment;
import types.IType;
import types.TFun;
import types.TRef;
import value.IValue;
import value.VFunc;

import javax.sound.sampled.Line;
import java.util.*;

public class ASTFunc implements ASTNode {
    private final Map<String, ASTNode> parameters;
    private final ASTNode body;
    private final Map<String, IType> namedTypes = new LinkedHashMap<>();
    private final List<IType> paramTypes = new LinkedList<>();
    private TFun functionType;

    public ASTFunc(Map<String, ASTNode> parameters, ASTNode body) {
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        return new VFunc(new ArrayList<>(parameters.keySet()), body, env);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        FrameStack frameStack = compiler.getfStack();
        Frame currentFrame = frameStack.newFrame();
        LineBuilder lb = new LineBuilder();
        lb.appendLine("new " + currentFrame);
        lb.appendLine("dup");
        lb.appendLine("invokespecial " + currentFrame + "/<init>()V");
        lb.appendLine("dup");
        lb.appendLine("aload 4");
        lb.appendLine("putfield " + currentFrame + "/sl L" + currentFrame.getParent().getFrameID() + ";");
        lb.appendLine("astore 4");

        ClosureInterface closureInterface = compiler.newClosureInterface(functionType);
        Closure closure = compiler.newClosure(currentFrame, closureInterface, namedTypes);
        currentFrame.addField(closure.getClosureId(), "L" + closure.getClosureId() + ";");
        Environment<IType> innerScope = env.startScope(closure.getClosureId());
        for (Map.Entry<String, IType> stringITypeEntry : namedTypes.entrySet()) {
            innerScope.associate(stringITypeEntry.getKey(), stringITypeEntry.getValue());
        }

        Assembler asm = body.compile(compiler, innerScope);
        lb.append(asm);
        return new Assembler(lb.toString(), asm.getStack(), asm.getType());
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        Environment<IType> innerScope = env.startScope();

        for (Map.Entry<String, ASTNode> param : parameters.entrySet()) {
            final String key = param.getKey();
            final IType paramType = param.getValue().typecheck(innerScope);
            innerScope.associate(key, paramType);
            namedTypes.put(key, paramType);
            paramTypes.add(paramType);
        }
        IType bodyType = body.typecheck(innerScope);
        functionType = new TFun(paramTypes, bodyType);
        return functionType;
    }

    @Override
    public String toString() {
        return String.format("fun %s -> %s", parameters.toString(), body.toString());
    }
}
