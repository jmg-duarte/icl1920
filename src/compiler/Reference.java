package compiler;

import types.IType;
import types.TBool;
import types.TInt;
import types.TRef;

import java.io.IOException;


public class Reference implements Dumpable {

    private final String refID;
    private final IType type;
    private final LineBuilder lb = new LineBuilder();

    public Reference(IType type) {
        this.type = type;
        this.refID = TRef.getReferenceClass(type);
        addHeader();
        addPrologue();
    }

    public String getReferenceID() {
        return refID;
    }

    @Override
    public void dump() throws IOException {
        lb.writeToFile(refID + ".j");
    }

    @Override
    public String toString() {
        return refID;
    }

    private void addHeader() {
        lb.appendLine(".class public " + TRef.getReferenceClass(type));
        lb.appendLine(".super java/lang/Object");
        lb.appendLine(".field public v " + type.getCompiledType());
    }

    private void addPrologue() {
        lb.appendLine(".method public <init>()V");
        lb.appendLine("aload_0");
        lb.appendLine("invokenonvirtual java/lang/Object/<init>()V");
        lb.appendLine("return");
        lb.appendLine(".end method");
    }
}
