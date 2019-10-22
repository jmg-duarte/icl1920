package compiler;

import ast.ASTNode;
import env.Environment;

public class Compiler {

    private LineBuilder lineBuilder = new LineBuilder();
    private Environment globalEnvironment = new Environment();

    private ASTNode root;

    public Compiler(ASTNode root) {
        this.root = root;
    }

    public void compile(ASTNode root) {
        root.compile(this, globalEnvironment);
    }
}
