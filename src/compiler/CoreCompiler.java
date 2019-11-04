package compiler;

import ast.ASTNode;
import env.Environment;

import java.io.IOException;

public class CoreCompiler {

    private LineBuilder lineBuilder = new LineBuilder();
    private Environment globalEnvironment = new Environment();

    private int frameID = 0;
    private String frameName;

    private ASTNode root;

    public CoreCompiler(ASTNode root) {
        this.root = root;
        frameName = "java/lang/Object;";
        frameID = 0;
    }

    public void compile(ASTNode root) {
        Assembler a = root.compile(this, globalEnvironment);
        lineBuilder.append(a);
        try {
            lineBuilder.writeToFile("out.j");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String oldFrame() {
        return frameName;
        //return "Frame_" + (frameID - 1);
    }

    public String newFrame() {
        frameName = "Frame_" + frameID++;
        return frameName;
        //return "Frame_" + frameID++;
    }

}
