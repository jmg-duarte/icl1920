package compiler;

import types.IType;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Frame implements Dumpable{

    public static final Frame ROOT = new Frame();

    final String frameId;
    final String frameFileName;
    Frame parent;
    Map<String, String> fields = new LinkedHashMap<>();

    private Frame() {
        this.frameId = "java/lang/Object";
        this.frameFileName = this.frameId + ".j";
    }

    public Frame(String frameId) {
        this.frameId = frameId;
        this.parent = null;
        this.frameFileName = this.frameId + ".j";
    }

    public Frame(String frameId, Frame parent) {
        this.frameId = frameId;
        this.frameFileName = this.frameId + ".j";
        addParent(parent);
    }

    public String getFrameId() {
        return frameId;
    }

    private void addParent(Frame parent) {
        this.parent = parent;
        addField("sl", String.format("L%s;", parent.frameId));
    }

    public void addField(String fieldId, String type) {
        fields.putIfAbsent(fieldId, type);
    }

    public void addField(String fieldId, IType type) {
        fields.putIfAbsent(fieldId, type.getCompiledType());
    }

    public Frame getParent() {
        return parent;
    }

    void writeHeader(LineBuilder lb) {
        lb.appendLine(".class public " + frameId);
        lb.appendLine(".super java/lang/Object");
    }

    void writeFields(LineBuilder lb) {
        for (Map.Entry<String, String> e : fields.entrySet()) {
            lb.appendLine(String.format(".field public %s %s", e.getKey(), e.getValue()));
        }
    }

    void writeInit(LineBuilder lb) {
        lb.appendLine(".method public <init>()V");
        lb.appendLine("aload_0");
        lb.appendLine("invokenonvirtual java/lang/Object/<init>()V");
        lb.appendLine("return");
        lb.appendLine(".end method");
    }

    @Override
    public void dump() throws IOException {
        final LineBuilder lb = new LineBuilder();
        writeHeader(lb);
        writeFields(lb);
        writeInit(lb);
        lb.writeToFile(frameFileName);
    }

    @Override
    public String toString() {
        return frameId;
    }

}
