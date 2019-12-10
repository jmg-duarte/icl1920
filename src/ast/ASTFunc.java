package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TFun;
import value.IValue;
import value.VFunc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ASTFunc implements ASTNode {
    private final Map<String,ASTNode> parameters;
    private List<IType> paramTypes;
    private final ASTNode body;
    private IType bodyType;

    public ASTFunc(Map<String, ASTNode> parameters, ASTNode body) {
        this.parameters = parameters;
        this.body = body;
        bodyType = null;
        paramTypes = null;
    }

    @Override
    public IValue eval(Environment env) {
        return new VFunc(new ArrayList<>(parameters.keySet()), body, env);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        Environment<IType> innerScope = env.startScope();
        paramTypes = new LinkedList<>();

        for(Map.Entry<String, ASTNode> param : parameters.entrySet()){
            IType paramType = param.getValue().typecheck(innerScope);
            innerScope.associate(param.getKey(),paramType);
            paramTypes.add(paramType);
        }
        bodyType = body.typecheck(innerScope);
        return new TFun(paramTypes, bodyType);
    }

}
