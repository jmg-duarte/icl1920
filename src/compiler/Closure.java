package compiler;

import types.IType;

import java.io.IOException;
import java.util.Map;

public class Closure implements Dumpable {

    private static int counter = 0;

    public static String getLabel() {
        return String.format("closure%s", counter++);
    }

    private final String closureId;
    private final Frame oldFrame;
    private final Frame closureFrame;
    private final ClosureInterface closureInterface;
    private final Map<String, IType> namedTypes;
    private final LineBuilder lb = new LineBuilder();

    public Closure(Frame closureFrame, ClosureInterface closureInterface, Map<String, IType> namedTypes) {
        this.closureId = Closure.getLabel();
        this.oldFrame = closureFrame.getParent();
        this.closureFrame = closureFrame;
        this.closureInterface = closureInterface;
        this.namedTypes = namedTypes;
        build();
    }

    public String getClosureId() {
        return closureId;
    }

    private void build() {
        lb.appendLine(".class " + closureId);
        lb.appendLine(".super java/lang/Object");
        lb.appendLine(String.format(".implements %s", closureInterface.getClosureTypeStr()));
        lb.appendLine(String.format(".field public sl L%s;", oldFrame.getFrameID())); // CARE NPE

        lb.appendLine(".method public <init>()V");
        lb.appendLine("aload_0");
        lb.appendLine("invokenonvirtual java/lang/Object/<init>()V");
        lb.appendLine("return");
        lb.appendLine(".end method");

        lb.appendLine(String.format(".method public %s", closureInterface.getCallTypeStr()));

        lb.appendLine(".limit locals 10");
        lb.appendLine(".limit stack 256");

        setupFrame();
        addLocals();

        lb.appendLine("ireturn");
        lb.appendLine(".end method");
    }

    private void setupFrame() {
        lb.appendLine(String.format("new %s", closureFrame.getFrameID()));
        lb.appendLine("dup");
        lb.appendLine(String.format("invokespecial %s/<init>()V", closureFrame.getFrameID()));
        lb.appendLine("dup");
        lb.appendLine("aload 0");
        lb.appendLine(String.format("getfield %s/sl L%s;", closureId, oldFrame.getFrameID()));
        lb.appendLine(String.format("putfield %s/sl L%s;", closureFrame.getFrameID(), oldFrame.getFrameID()));
        lb.appendLine("dup");
    }

    private void addLocals() {
        int counter = 1;
        for (String name : namedTypes.keySet()) {
            lb.appendLine(String.format("aload %d", counter));
            lb.appendLine(String.format("putfield %s/_%s %s", closureId, name, namedTypes.get(name).getCompiledType()));
            lb.appendLine("dup");
        }
        lb.appendLine("pop"); // TODO CARE
        lb.appendLine("astore 0");
    }

    @Override
    public void dump() throws IOException {
        lb.writeToFile(closureId + ".j");
    }
}
