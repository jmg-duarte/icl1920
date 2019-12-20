package compiler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Interface implements Dumpable {

    final String interfaceName;
    final String interfaceFileName;
    final List<String> methods = new LinkedList<>();

    public Interface(String interfaceName) {
        this.interfaceName = interfaceName;
        interfaceFileName = interfaceName + ".j";
    }

    public void addMethod(String method) {
        methods.add(method);
    }

    @Override
    public void dump() throws IOException {
        LineBuilder lb = new LineBuilder();
        writeHeader(lb);
        writeMethods(lb);
        writeFooter(lb);
        lb.writeToFile(interfaceFileName);
    }

    private void writeHeader(LineBuilder lb) {
        lb.appendLine(String.format(".interface %s", interfaceName));
        lb.appendLine(".super java/lang/Object");
    }

    private void writeMethods(LineBuilder lb) {
        for (String method : methods) {
            lb.appendLine(".method public abstract " + method);
        }
    }

    private void writeFooter(LineBuilder lb) {
        lb.appendLine("return");
        lb.appendLine(".end method");
    }
}
