package ast;

import compiler.*;
import env.Environment;
import types.IType;
import types.TFun;
import value.IValue;
import value.VFunc;

import java.util.*;

public class ASTFunc implements ASTNode {
    private final Map<String, ASTNode> parameters;
    private final ASTNode body;
    private final Map<String, IType> namedTypes = new LinkedHashMap<>();
    private final List<IType> paramTypes = new LinkedList<>();

    private TFun functionType;
    private Closure closure;

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
        ClosureInterface closureInterface = compiler.newClosureInterface(functionType);
        Closure closure = compiler.newClosure(closureInterface);
        Frame closureFrame = closure.getClosureFrame();
        Environment<IType> innerScope = env.startScope(closureFrame.getFrameId());
        for (Map.Entry<String, IType> e : namedTypes.entrySet()) {
            closureFrame.addField(e.getKey(), e.getValue().getCompiledType());
            innerScope.associate(e.getKey(), e.getValue());
        }
        String funcAsm = generateFunctionAsm(closure);
        Assembler asm = body.compile(compiler, innerScope);
        closure.addBody(asm.toString());
        /*
        Frame currentFrame = frameStack.newClosureFrame(closureInterface);

        LineBuilder lb = new LineBuilder();
        lb.appendLine("new " + currentFrame);
        lb.appendLine("dup");
        lb.appendLine("invokespecial " + currentFrame + "/<init>()V");
        lb.appendLine("dup");
        lb.appendLine("aload 4");
        lb.appendLine("putfield " + currentFrame + "/sl L" + currentFrame.getParent().getFrameID() + ";");
        lb.appendLine("astore 4");

        // Closure closure = compiler.newClosure(currentFrame, closureInterface, namedTypes);

        currentFrame.addField(closure.getClosureId(), "L" + closure.getClosureId() + ";");

        Environment<IType> innerScope = env.startScope(closure.getClosureId());
        for (Map.Entry<String, IType> e : namedTypes.entrySet()) {
            innerScope.associate(e.getKey(), e.getValue());
        }

        Assembler asm = body.compile(compiler, innerScope);
        lb.append(asm);
        */
        return new Assembler(funcAsm, asm.getStack(), new TFun(functionType, closure.getFrameId()));
    }

    private String generateFunctionAsm(Frame frame) {
        LineBuilder lb = new LineBuilder();
        lb.appendLine("new " + frame);
        lb.appendLine("dup");
        lb.appendLine("invokespecial " + frame + "/<init>()V");
        lb.appendLine("dup");
        lb.appendLine("aload 4");
        lb.appendLine("putfield " + frame + "/sl L" + frame.getParent().getFrameId() + ";");
        return lb.toString();
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
