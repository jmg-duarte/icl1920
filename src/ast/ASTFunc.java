package ast;

import compiler.*;
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
    private final Map<String, ASTNode> parameters;
    private final ASTNode body;
    private final List<IType> paramTypes = new LinkedList<>();
    private IType bodyType;
    private TFun functionType;

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
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Environment innerScope = env.startScope();
        LineBuilder lb = new LineBuilder();

        //ClosureInterface funClosure = compiler.newClosure();
      //  Closure newClosure = funClosure.createClosure(paramsTypes,bodyType,env);

       // lb.appendLine(".class " + newClosure.getClosureId());
        lb.appendLine(".implements");
        lb.appendLine(".field");
        lb.appendLine("method call");
        return new Assembler(lb.toString(),0, functionType);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        Environment<IType> innerScope = env.startScope();

        for (Map.Entry<String, ASTNode> param : parameters.entrySet()) {
            final String key = param.getKey();
            final IType paramType = param.getValue().typecheck(innerScope);
            innerScope.associate(key, paramType);
            paramTypes.add(paramType);
        }
        bodyType = body.typecheck(innerScope);
        functionType = new TFun(paramTypes, bodyType);
        return functionType;
    }

}
