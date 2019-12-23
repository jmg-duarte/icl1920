package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import value.IValue;

public class ASTSequence implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;
    private IType type;

    public ASTSequence(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        head.eval(env);
        return tail.eval(env);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        LineBuilder lb = new LineBuilder();
        Assembler headComp = head.compile(compiler, env);
        Assembler tailComp = tail.compile(compiler, env);
        lb.append(headComp);
        lb.appendLine("pop");
        lb.append(tailComp);
        return new Assembler(lb.toString(), headComp.getStack() + tailComp.getStack(), type);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        head.typecheck(env);
        type = tail.typecheck(env);
        return type;
    }

    @Override
    public IType getType() {
        return type;
    }
}
