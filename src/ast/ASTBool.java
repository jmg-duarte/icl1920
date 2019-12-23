package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TBool;
import value.IValue;
import value.TypeErrorException;
import value.VBool;

public class ASTBool implements ASTNode {

    private final Boolean exp;

    public ASTBool(Boolean exp) {
        this.exp = exp;
    }

    public static ASTBool True() {
        return new ASTBool(true);
    }

    public static ASTBool False() {
        return new ASTBool(false);
    }

    @Override
    public IValue eval(Environment<IValue> env) throws TypeErrorException {
        return new VBool(exp);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        String code;
        if (exp) {
            code = "sipush 1";
        } else {
            code = "sipush 0";
        }
        return new Assembler(code, 1, TBool.TYPE);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return TBool.TYPE;
    }

    @Override
    public IType getType() {
        return TBool.TYPE;
    }

    @Override
    public String toString() {
        return exp.toString();
    }
}
