package compiler;

import types.IType;
import types.TBool;
import types.TInt;
import types.TRef;

import java.io.IOException;


public class Reference {

    private final String refID;
    private final LineBuilder lb = new LineBuilder();

    public Reference(IType type) {
        this.refID = TRef.getReferenceClass(type);
        lb.appendLine(".class public " + TRef.getReferenceClass(type));
        lb.appendLine(".super java/lang/Object");
        lb.appendLine(".field public v " + type.getCompiledType());
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
