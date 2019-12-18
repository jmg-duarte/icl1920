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
    private TRef type;

    public ASTDeref(ASTNode expression) {
        this.expression = expression;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        return VRef.check(expression.eval(env)).get();
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        LineBuilder lb = new LineBuilder();
        Assembler expAsm = expression.compile(compiler, env);
        if (type.getInnerType() instanceof TBool || type.getInnerType() instanceof TInt) {
            lb.append(expAsm);
            lb.appendLine("checkcast ref_int");
            lb.appendLine("getfield ref_int/v " + type.toString());
        }
        if (type.getInnerType() instanceof TRef) {
            lb.append(expAsm);
            lb.appendLine("checkcast ref_class");
            lb.appendLine("getfield ref_class/v " + type.toString());
        }
        return new Assembler(lb.toString(), expAsm.getStack(), type);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType exprType = expression.typecheck(env);
        if (!(exprType instanceof TRef)) {
            throw new TypeErrorException("expression must be of type ref");
        }
        type = ((TRef) exprType);
        return type;
    }
}
