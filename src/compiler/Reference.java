package compiler;

import java.io.IOException;

public class Reference {

    private final String refID;
    private final LineBuilder lb = new LineBuilder();

    public Reference(String refID, String type) {
        this.refID = refID;
        lb.appendLine(".class " + refID);
        lb.appendLine(".super java/lang/Object");
        lb.appendLine(".field public " + type + ";");
    }

    public String getReferenceID() {
        return refID;
    }

    public void dumpReference() throws IOException {
        lb.appendLine(".method public <init>()V");
        lb.appendLine("aload_0");
        lb.appendLine("invokenonvirtual java/lang/Object/<init>()V");
        lb.appendLine("return");
        lb.appendLine(".end method");
        lb.writeToFile(refID + ".j");
    }

    @Override
    public String toString() {
        return refID;
    }
}
