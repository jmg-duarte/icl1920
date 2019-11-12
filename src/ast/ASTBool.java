package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.TypeErrorException;
import value.VBool;

public class ASTBool implements ASTNode {

    Boolean exp1;

    public ASTBool(Boolean exp1){
        this.exp1 = exp1;
    }

    @Override
    public IValue eval(Environment env) throws TypeErrorException{
        return new VBool(exp1);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }
}
