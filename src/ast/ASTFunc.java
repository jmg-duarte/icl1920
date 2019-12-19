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

        ClosureInterface closureInterface = compiler.newClosureInterface(functionType);
        Closure closure = new Closure(currentFrame, closureInterface, namedTypes);

        Environment<IType> innerScope = env.startScope(currentFrame.getFrameID());
        for (Map.Entry<String, IType> stringITypeEntry : namedTypes.entrySet()) {
            innerScope.associate(stringITypeEntry.getKey(), stringITypeEntry.getValue());
        }

        Assembler asm = body.compile(compiler, innerScope);

        return asm;
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
