package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.TypeErrorException;
import value.VBool;

public class ASTIf implements ASTNode{
    ASTNode exp1;
    ASTNode exp2;
    ASTNode exp3;

    public ASTIf(ASTNode exp1, ASTNode exp2, ASTNode exp3){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public IValue eval(Environment env) throws TypeErrorException {
        Environment innerScope = env.startScope();
        IValue v1 = exp1.eval(env);
        if (v1 instanceof VBool){
            if (((VBool) v1).getValue())
                return exp2.eval(innerScope);
             else
                return exp3.eval(innerScope);
        } else {
            throw new TypeErrorException("illegal arguments");
        }
    }

    //TODO ASTIf compile method
    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }


}
