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
        expression.compile(compiler,env);
        LineBuilder lb = new LineBuilder();
        lb.appendLine("checkcast ");
       // lb.appendLine("getfield "+ref+"/v Ljava/lang/Object");
        //TODO ver o tamanho da stack
        return new Assembler(lb.toString(),0);
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
