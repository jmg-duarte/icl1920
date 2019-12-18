package ast;

import compiler.Assembler;
import compiler.CoreCompiler;
import compiler.LabelMaker;
import compiler.LineBuilder;
import env.Environment;
import types.IType;
import types.TBool;
import value.IValue;
import value.TypeErrorException;
import value.VBool;

public class ASTIf implements ASTNode {
    private final ASTNode conditional;
    private final ASTNode ifTrue;
    private final ASTNode ifFalse;
    private IType type;

    public ASTIf(ASTNode conditional, ASTNode ifTrue, ASTNode ifFalse) {
        this.conditional = conditional;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws TypeErrorException {
        Environment<IValue> innerScope = env.startScope();
        IValue v1 = conditional.eval(env);
        if (!(v1 instanceof VBool)) {
            throw new TypeErrorException();
        }
        if (((VBool) v1).getValue()) {
            return ifTrue.eval(innerScope);
        } else {
            return ifFalse.eval(innerScope);
        }
    }

    //TODO ASTIf compile method
    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Assembler condAssembly = conditional.compile(compiler, env);
        Assembler trueAssembly = ifTrue.compile(compiler, env);
        Assembler falseAssembly = ifFalse.compile(compiler, env);

        String labelFalse = LabelMaker.getLabel();
        String labelTrue = LabelMaker.getLabel();

        LineBuilder lb = new LineBuilder();
        lb.append(condAssembly);
        lb.appendLine(Assembler.IF_EQUALS + " " + labelFalse);
        lb.append(trueAssembly);
        lb.appendLine(Assembler.GO_TO + " " + labelTrue);
        lb.appendLine(labelFalse + ":");
        lb.append(falseAssembly);
        lb.appendLine(labelTrue + ":");

        return new Assembler(lb.toString(), condAssembly.getStack() +
                trueAssembly.getStack() +
                falseAssembly.getStack(), type);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        final IType conditionalType = conditional.typecheck(env);
        if (!(conditionalType instanceof TBool)) {
            throw new TypeErrorException("invalid conditional expression");
        }

        final IType ifTrueType = ifTrue.typecheck(env);
        final IType ifFalseType = ifFalse.typecheck(env);
        if (!(ifTrueType.equals(ifFalseType))) {
            throw new TypeErrorException("if branches must be of the same type");
        }
        type = ifTrueType;
        return type;
    }
}



