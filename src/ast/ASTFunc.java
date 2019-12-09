package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TFun;
import value.IValue;
import value.VFunc;

import java.util.ArrayList;
import java.util.Map;

public class ASTFunc implements ASTNode {
    private final Map<String,IType> parameters;
    private final ASTNode body;
    private IType bodyType;

    public ASTFunc(Map<String, IType> parameters, ASTNode body) {
        this.parameters = parameters;
        this.body = body;
        bodyType = null;
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

        for(Map.Entry<String, IType> param : parameters.entrySet()){
            innerScope.associate(param.getKey(),param.getValue());
        }
        bodyType = body.typecheck(innerScope);
        return new TFun(new ArrayList<>(parameters.values()), bodyType);
    }

}
