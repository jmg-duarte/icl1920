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

public class ASTNot implements ASTNode {
    private ASTNode exp;

    public ASTNot(ASTNode exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        VBool result = VBool.check(exp.eval(env));
        return new VBool(!result.getValue());
    }

    @Override
    public Assembler compile(CoreCompiler compiler, Environment<IType> env) {
        Assembler leftAssembly = exp.compile(compiler, env);

        String labelTrue = LabelMaker.getLabel();
        String labelFalse = LabelMaker.getLabel();

        LineBuilder lb = new LineBuilder();
        lb.append(leftAssembly);

        lb.appendLine(Assembler.IF_EQUALS + " " + labelTrue);
        lb.appendLine(Assembler.BOOLEAN_FALSE);
        lb.appendLine(Assembler.GO_TO + " " + labelFalse);
        lb.appendLine(labelTrue + ": ");
        lb.appendLine(Assembler.BOOLEAN_TRUE);
        lb.appendLine(labelFalse + ": ");

        return new Assembler(lb.toString(), leftAssembly.getStack(), TBool.TYPE);
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        IType type = exp.typecheck(env);
        if (!(type instanceof TBool)) {
            throw new TypeErrorException();
        }
        return TBool.TYPE;
    }
}
