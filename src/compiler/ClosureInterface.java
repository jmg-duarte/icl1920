package compiler;

import java.io.IOException;

public class ClosureInterface {

    private final String interfaceType;
    private final String functionType;
    private final LineBuilder lb = new LineBuilder();

    public ClosureInterface(String interfaceType, String functionType) {
        this.interfaceType = interfaceType;
        this.functionType = functionType;
        lb.appendLine(".interface " + interfaceType);
        lb.appendLine(".method call"+functionType);
        lb.appendLine(".end");
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void addField(String fieldID) {
       // lb.appendLine(".field public _" + fieldID + " I");
    }

    public void dumpFrame() throws IOException {
        /*lb.appendLine(".method public <init>()V");
        lb.appendLine("aload_0");
        lb.appendLine("invokenonvirtual java/lang/Object/<init>()V");
        lb.appendLine("return");
        lb.appendLine(".end method");
        lb.writeToFile(interfaceType + ".j");*/
    }

    @Override
    public String toString() {
        return interfaceType;
    }
}
