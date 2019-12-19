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

public class ASTAssign implements ASTNode {

    private final ASTNode leftNode;
    private final ASTNode rightNode;
    private IType leftType;
    private IType rightType;

    public ASTAssign(ASTNode leftNode, ASTNode rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        VRef ref = VRef.check(leftNode.eval(env));
        ref.set(rightNode.eval(env));
        return ref;
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Assembler leftAsm = leftNode.compile(compiler, env);
        Assembler rightAsm = rightNode.compile(compiler, env);
        return new Assembler(
                getAssignAsm(leftAsm, rightAsm),
                leftAsm.getStack() + rightAsm.getStack(),
                leftType);
    }

    private String getAssignAsm(Assembler leftAsm, Assembler rightAsm) {
        // final IType rightType = rightAsm.getType();
        final String ref = TRef.getReferenceClass(rightType);
        final LineBuilder lb = new LineBuilder();
        lb.append(leftAsm);
        lb.appendLine(String.format("checkcast %s", ref));
        lb.append(rightAsm);
        lb.appendLine(String.format("putfield %s/v %s", ref, rightType.getCompiledType()));
        return lb.toString();
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        leftType = leftNode.typecheck(env);
        if (!(leftType instanceof TRef)) {
            throw new TypeErrorException("assigned value must be of type ref");
        }
        IType innerType = ((TRef) leftType).getInnerType();
        rightType = rightNode.typecheck(env);
        if (!(rightType.equals(innerType))) {
            throw new TypeErrorException("right hand value type must match reference type");
        }
        return leftType;
    }

    @Override
    public String toString() {
        return String.format("%s := %s", leftNode.toString(), rightNode.toString());
    }
}
