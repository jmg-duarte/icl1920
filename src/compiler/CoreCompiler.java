package compiler;

import ast.ASTNode;
import env.Environment;
import value.IValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CoreCompiler {

    private LineBuilder lineBuilder = new LineBuilder();
    private Environment<IValue> globalEnvironment = new Environment();
    private FrameStack fStack = new FrameStack();

    private ASTNode root;

    public CoreCompiler(ASTNode root) {
        this.root = root;
    }

    public void compile(ASTNode root) {
        Assembler a = root.compile(this, globalEnvironment);
        lineBuilder.append(a);
        try {
            lineBuilder.addHeader();
            lineBuilder.addFooter();
            lineBuilder.writeToFile("Main.j");
            fStack.dumpStack();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public FrameStack getfStack() {
        return fStack;
    }


}
