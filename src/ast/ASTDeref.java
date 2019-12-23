package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
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

    private static String getDerefAsm(IType type) {
        LineBuilder lb = new LineBuilder();
        String ref = TRef.getReferenceClass(type);
        lb.appendLine("checkcast " + ref);
        lb.appendLine(String.format("getfield %s/v %s", ref, type.getCompiledType()));
        return lb.toString();
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
        Assembler expAsm = expression.compile(compiler, env);
        return new Assembler(getAsm(expAsm), expAsm.getStack(), type);
    }

    private String getAsm(Assembler expAsm) {
        LineBuilder lb = new LineBuilder();
        lb.append(expAsm);
        lb.append(getDerefAsm(type));
        return lb.toString();
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
    public IType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("!%s", expression.toString());
    }
}
