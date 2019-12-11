package ast.types;

import ast.ASTNode;
import compiler.Assembler;
import compiler.CoreCompiler;
import env.Environment;
import types.IType;
import types.TRef;
import value.IValue;

public class ASTRefType extends ASTType {
    private final ASTNode contentType;

    public ASTRefType(ASTNode type) {
        this.contentType = type;
    }

    @Override
    public IType typecheck(Environment<IType> env) {
        return new TRef(contentType.typecheck(env));
    }
}
