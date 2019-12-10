package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TFun;
import value.IValue;

import java.util.LinkedList;
import java.util.List;

public class ASTFunType implements ASTNode {
    private List<ASTNode> paramTypes;
    private ASTNode bodyType;

    public ASTFunType(List<ASTNode> paramTypes, ASTNode bodyType){
        this.paramTypes = paramTypes;
        this.bodyType = bodyType;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        return null;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IValue> env) {
        return null;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        List<IType> types = new LinkedList<IType>();
        for (ASTNode param : paramTypes){
            types.add(param.typecheck(env));
        }
       return new TFun(types, bodyType.typecheck(env));
    }
}
