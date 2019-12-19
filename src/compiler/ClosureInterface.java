package compiler;

import types.TFun;

import java.io.IOException;

public class ClosureInterface implements Dumpable {

    private final LineBuilder lb = new LineBuilder();
    private final TFun functionType;
    private final String closureTypeStr;
    private final String callTypeStr;

    public ClosureInterface(TFun type) {
        functionType = type;
        closureTypeStr = "closure_interface_type_" + type.getClosureType();
        callTypeStr = "call" + type.getCompiledType();
        setInterface(type);
    }

    private void setInterface(TFun type) {
        lb.appendLine(String.format(".interface %s", closureTypeStr));
        lb.appendLine(String.format(".method %s", callTypeStr));
        lb.appendLine(".end");
    }

    public String getClosureTypeStr() {
        return closureTypeStr;
    }

    public String getCallTypeStr() {
        return callTypeStr;
    }

    public TFun getFunctionType() {
        return functionType;
    }

    @Override
    public void dump() throws IOException {
        lb.writeToFile(String.format("IClosure_%s.j", closureTypeStr));
    }
}
