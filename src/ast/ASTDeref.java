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
    private IType type;

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
        if (type instanceof TBool || type instanceof TInt) {
            lb.append(expAsm);
            lb.appendLine("checkcast ref_int");
            lb.appendLine("getfield ref_int/v " + type.toString());
        }
        if (type instanceof TRef) {
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
            throw new TypeErrorException(String.format("expression must be of type ref, got: %s", exprType.toString()));
        }
        type = ((TRef) exprType).getInnerType();
        return type;
    }

    @Override
    public String toString() {
        return String.format("!%s", expression.toString());
    }
}
