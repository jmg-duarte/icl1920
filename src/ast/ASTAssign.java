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
        LineBuilder lb = new LineBuilder();
        Assembler leftAsm = leftNode.compile(compiler, env);
        Assembler rightAsm = rightNode.compile(compiler, env);

        if (rightType instanceof TBool || rightType instanceof TInt) {
            lb.append(leftAsm);
            lb.appendLine("checkcast ref_int");
            lb.append(rightAsm);
            lb.appendLine("putfield ref_int/v " + rightType.toString());
        }
        if (rightType instanceof TRef) {
            lb.append(leftAsm);
            lb.appendLine("checkcast ref_class");
            lb.append(rightAsm);
            rightNode.compile(compiler, env);
            lb.appendLine("putfield ref_class/v " + rightType.toString());
        }
        return new Assembler(lb.toString(), leftAsm.getStack() + rightAsm.getStack(), leftAsm.getType());
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        leftType = leftNode.typecheck(env);
        if (!(leftType instanceof TRef)) {
            throw new TypeErrorException("assigned value must be of type \"ref\"");
        }
        IType innerType = ((TRef) leftType).getInnerType();
        rightType = rightNode.typecheck(env);
        if (!(rightType.equals(innerType))) {
            throw new TypeErrorException("right hand value type must match reference type");
        }
        return leftType;
    }

}
