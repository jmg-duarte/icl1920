package compiler;

import types.IType;
import types.TBool;
import types.TInt;

import java.io.IOException;
import java.util.Map;

public class Closure extends Frame {

    private final Frame closureFrame;
    private final ClosureInterface closureInterface;
    private String closureBody;

    public Closure(String frameID, ClosureInterface closureInterface) {
        super(frameID);
        this.closureFrame = new Frame("f" + frameID);
        this.closureInterface = closureInterface;
    }

    public Closure(String frameID, ClosureInterface closureInterface, Frame parent) {
        super(frameID, parent);
        this.closureFrame = new Frame("f" + frameID, parent);
        this.closureInterface = closureInterface;
    }

    public Frame getClosureFrame() {
        return closureFrame;
    }

    public void addBody(String body) {
        closureBody = body;
    }

    private void writeClosureMethod(LineBuilder lb) {
        writeMethodHeader(lb);
        writeClosureFrameInit(lb);
        writeParameterFetch(lb);
        writeBody(lb);
        writeMethodFooter(lb);
    }

    private void writeClosureFrameInit(LineBuilder lb) {
        lb.appendLine(String.format("new %s", closureFrame.frameId));
        lb.appendLine("dup");
        lb.appendLine(String.format("invokespecial %s/<init>()V", closureFrame.frameId));
        lb.appendLine("dup");
        lb.appendLine("aload 0");
        lb.appendLine(String.format("getfield %s/sl L%s;", this.frameId, parent.frameId));
        lb.appendLine(String.format("putfield %s/sl L%s;", closureFrame.frameId, parent.frameId));
        lb.appendLine("astore 4");
        lb.appendLine("aload 4");
        lb.appendLine("dup");
    }

    private void writeParameterFetch(LineBuilder lb) {
        int idx = 1;
        for (Map.Entry<String, String> e : closureFrame.fields.entrySet()) {
            if ("sl".equals(e.getKey())){
                continue;
            }
            switch (e.getValue()) {
                case "I":
                case "Z":
                    lb.appendLine(String.format("iload %d", idx));
                    break;
                default:
                    lb.appendLine(String.format("aload %d", idx));
                    break;
            }
            lb.appendLine(String.format("putfield %s/%s %s", closureFrame.frameId, e.getKey(), e.getValue()));
            // lb.appendLine("dup");
            idx++;
        }
    }

    private void writeBody(LineBuilder lb) {
        lb.appendLine(closureBody);
    }

    private void writeMethodHeader(LineBuilder lb) {
        lb.appendLine(String.format(".method public %s", closureInterface.getCallTypeStr()));
        lb.appendLine(".limit locals 10");
        lb.appendLine(".limit stack 256");
    }

    private void writeMethodFooter(LineBuilder lb) {
        IType returnType = closureInterface.getReturnType();
        if (returnType instanceof TBool || returnType instanceof TInt) {
            lb.appendLine("ireturn");
        } else {
            lb.appendLine("areturn");
        }
        lb.appendLine(".end method");
    }

    private void writeImplements(LineBuilder lb) {
        lb.appendLine(String.format(".implements %s", closureInterface.getClosureTypeStr()));
    }

    @Override
    public void dump() throws IOException {
        final LineBuilder lb = new LineBuilder();
        writeHeader(lb);
        writeImplements(lb);
        writeFields(lb);
        writeInit(lb);
        writeClosureMethod(lb);
        lb.writeToFile(this.frameFileName);
    }
}
