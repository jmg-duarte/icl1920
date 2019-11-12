package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import value.IValue;
import value.TypeErrorException;
import value.VBool;

public class ASTBooleanOp implements ASTNode{

    ASTNode exp1;
    ASTNode exp2;
    String op;

    public ASTBooleanOp(ASTNode exp1, ASTNode exp2, String op){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(Environment env) throws TypeErrorException {
        IValue v1 = exp1.eval(env);
        IValue v2 = exp2.eval(env);
        if (v1 instanceof VBool && v2 instanceof VBool)
            if (op.equals("&&"))
                return new VBool(((VBool) v1).getValue() && ((VBool) v2).getValue());
            else
                return new VBool(((VBool) v1).getValue() || ((VBool) v2).getValue());
        throw new TypeErrorException("wrong arguments");
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }


}
