package ast;

        import compiler.Assembler;
        import compiler.CoreCompiler;
        import env.Environment;
        import value.IValue;
        import value.TypeErrorException;

public interface ASTNode {
    IValue eval(Environment env) throws TypeErrorException;

    Assembler compile(CoreCompiler compiler, Environment env);
}