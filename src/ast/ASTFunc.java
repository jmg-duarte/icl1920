package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TFun;
import value.IValue;
import value.VFunc;

import java.util.*;

public class ASTFunc implements ASTNode {
    private final Map<String, ASTNode> parameters;
    private final List<IType> paramTypes = new LinkedList<>();
    private final ASTNode body;
    private IType bodyType;

    public ASTFunc(Map<String, ASTNode> parameters, ASTNode body) {
        this.parameters = parameters;
        this.body = body;
        bodyType = null;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        return new VFunc(new ArrayList<>(parameters.keySet()), body, env);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        Environment newEnv = env.startScope();
        //body.compile(newEnv, )
        return null;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        Environment<IType> innerScope = env.startScope();

        for (Map.Entry<String, ASTNode> param : parameters.entrySet()) {
            final IType paramType = param.getValue().typecheck(innerScope);
            final String key = param.getKey();
            innerScope.associate(key, paramType);
            paramTypes.add(paramType);
        }
        bodyType = body.typecheck(innerScope);
        return new TFun(paramTypes, bodyType);
    }

}
