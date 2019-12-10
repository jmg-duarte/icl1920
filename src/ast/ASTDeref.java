package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TBool;
import types.TInt;
import types.TRef;
import value.IValue;
import value.TypeErrorException;
import value.VRef;

public class ASTDeref implements ASTNode {

    private final ASTNode expression;
    private IType contentType;

    public ASTDeref(ASTNode expression) {
        this.expression = expression;
        contentType = null;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public IValue eval(Environment env) {
        return VRef.check(expression.eval(env)).get();
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        LineBuilder lb = new LineBuilder();
        Assembler expAsm = expression.compile(compiler, env);
        if (contentType instanceof TBool || contentType instanceof TInt) {
            lb.append(expAsm);
            lb.appendLine("checkcast ref_int");
            lb.appendLine("getfield ref_int/v " + contentType.toString());
        }
        if (contentType instanceof TRef) {
            lb.append(expAsm);
            lb.appendLine("checkcast ref_class");
            lb.appendLine("getfield ref_class/v " + contentType.toString());
        }
        return new Assembler(lb.toString(),expAsm.getStack());
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType exprType = expression.typecheck(env);
        if(!(exprType instanceof TRef)){
            throw new TypeErrorException();
        }
        //contentType = ((TRef)exprType).getType(); para o compile
        return exprType;
    }
}
