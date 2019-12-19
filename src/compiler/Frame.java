package compiler;

import types.IType;

import java.io.IOException;

public class Frame implements Dumpable{

    private final String frameID;
    private final Frame parent;
    private final LineBuilder lb = new LineBuilder();

    public Frame(String frameID) {
        this.frameID = frameID;
        this.parent = null;
    }

    public Frame(String frameID, Frame parent) {
        this.frameID = frameID;
        this.parent = parent;
        lb.appendLine(".class " + frameID);
        lb.appendLine(".super java/lang/Object");
        lb.appendLine(".field public sl L" + parent.frameID + ";");
    }

    public Frame(int counter, Frame parent) {
        this("f" + counter, parent);
    }

    public String getFrameID() {
        return frameID;
    }

    public void addField(String fieldID, IType type) {
        lb.appendLine(String.format(".field public _%s %s", fieldID, type.getCompiledType()));
    }

    public Frame getParent() {
        return parent;
    }

    @Override
    public void dump() throws IOException {
        lb.appendLine(".method public <init>()V");
        lb.appendLine("aload_0");
        lb.appendLine("invokenonvirtual java/lang/Object/<init>()V");
        lb.appendLine("return");
        lb.appendLine(".end method");
        lb.writeToFile(frameID + ".j");
    }

    @Override
    public String toString() {
        return frameID;
    }
}
