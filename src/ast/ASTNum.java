package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TInt;
import value.IValue;
import value.VInt;

public class ASTNum implements ASTNode {
    private int val;

    public ASTNum(int val) {
        this.val = val;
    }

    @Override
    public IValue eval(Environment env) {
        return new VInt(val);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        String code = "sipush " + val;
        return new Assembler(code, 1);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return new TInt();
    }
}
