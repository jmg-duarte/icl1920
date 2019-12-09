package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import value.IValue;

public class ASTSequence implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;

    public ASTSequence(ASTNode head, ASTNode tail){
        this.head = head;
        this.tail = tail;
    }

    @Override
    public IValue eval(Environment env) {
        IValue v1 = head.eval(env);
        return tail.eval(env);
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment env) {
        return null;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType headType = head.typecheck(env);
        IType tailType = tail.typecheck(env);
        return tailType;
    }
}
