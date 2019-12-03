package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import value.IValue;
import value.VBool;

public class ASTNot implements ASTNode {
    ASTNode exp;

    public ASTNot(ASTNode exp){
        this.exp = exp;
    }

    @Override
    public IValue eval(Environment env) {
        VBool result = VBool.check(exp.eval(env));
        return new VBool(!result.getValue());
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return null;
    }
}
