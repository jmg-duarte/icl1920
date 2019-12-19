package compiler;

import types.IType;

import java.io.IOException;
import java.util.Map;

public class Closure implements Dumpable {

    private static int counter = 0;
    public static String getLabel() {
        return String.format("clojure%s", counter++);
    }

    private final String closureId;
    private final Frame oldFrame;
    private final Frame closureFrame;
    private final ClosureInterface closureInterface;
    private final Map<String, IType> namedTypes;
    private final LineBuilder lb = new LineBuilder();

    /*
     //ClosureInterface funClosure = compiler.newClosure();
      //  Closure newClosure = funClosure.createClosure(paramsTypes,bodyType,env);

       // lb.appendLine(".class " + newClosure.getClosureId());
        lb.appendLine(".implements");
        lb.appendLine(".field");
        lb.appendLine("method call");
     */

    public Closure(Frame closureFrame, ClosureInterface closureInterface, Map<String, IType> namedTypes) {
        this.closureId = Closure.getLabel();
        this.oldFrame = closureFrame.getParent();
        this.closureFrame = closureFrame;
        this.closureInterface = closureInterface;
        this.namedTypes = namedTypes;
    }

    private void build() {
        lb.appendLine(".class closure_" + closureId);
        lb.appendLine(String.format(".implements %s", closureInterface.getClosureTypeStr()));
        lb.appendLine(String.format(".field sl L%s", oldFrame.getFrameID())); // CARE NPE
        lb.appendLine(String.format("method %s", closureInterface.getCallTypeStr()));
        setupFrame();
        addLocals();

        lb.appendLine("return");
        lb.appendLine(".end");
    }

    private void setupFrame() {
        lb.appendLine(String.format("new %s", closureFrame.getFrameID()));
        lb.appendLine("dup");
        lb.appendLine("aload 0");
        lb.appendLine(String.format("getfield %s/sl L%s", closureId, oldFrame.getFrameID()));
        lb.appendLine(String.format("putfield %s/sl L%s", closureFrame.getFrameID(), oldFrame.getFrameID()));
        lb.appendLine("dup");
    }

    private void addLocals() {
        int counter = 1;
        for (String name : namedTypes.keySet()) {
            lb.appendLine(String.format("aload %d", counter));
            lb.appendLine(String.format("putfield %s/%s %s", closureFrame.getFrameID(), name, namedTypes.get(name).getCompiledType()));
            lb.appendLine("dup");
        }
        lb.appendLine("pop"); // TODO CARE
        lb.appendLine("astore 0");
    }

    @Override
    public void dump() throws IOException {

    }
}
