package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import value.IValue;

public class ASTPrint implements ASTNode {
    ASTNode exp;

    public ASTPrint(ASTNode exp){
        this.exp = exp;
    }

    @Override
    public IValue eval(Environment env) {
        System.out.println(exp.eval(env));
        return null;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
        //ir ao rodape -> converte para string e faz o método print ;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return null;
    }
}
