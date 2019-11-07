package compiler;

import ast.ASTNode;
import env.Environment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CoreCompiler {

    private LineBuilder lineBuilder = new LineBuilder();
    private Environment globalEnvironment = new Environment();

    private int frameCounter = 0;
    private Map<String, Frame> frames = new HashMap<>();
    private Frame oldFrame;

    private ASTNode root;

    public CoreCompiler(ASTNode root) {
        this.root = root;
        oldFrame = new Frame("java/lang/Object");
        frameCounter = 0;
    }

    public void compile(ASTNode root) {
        Assembler a = root.compile(this, globalEnvironment);
        lineBuilder.append(a);
        try {
            lineBuilder.addHeader();
            lineBuilder.addFooter();
            lineBuilder.writeToFile("out.j");
            for (Map.Entry<String, Frame> entry : frames.entrySet()) {
                entry.getValue().dumpFrame();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public int frameDepth() {
        return frameCounter;
    }

    public Frame getOldFrame() {
        return oldFrame;
    }

    public Frame newFrame() {
        Frame frame = new Frame(frameCounter++, oldFrame);
        frames.put(frame.getFrameID(), frame);
        oldFrame = frame;
        return frame;
    }

}
