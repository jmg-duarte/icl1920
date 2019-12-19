package compiler;

import ast.ASTNode;
import env.Environment;
import types.IType;
import types.TFun;
import value.IValue;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CoreCompiler {

    private LineBuilder lineBuilder = new LineBuilder();
    private Environment<IType> globalEnvironment = new Environment<>();
    private FrameStack fStack = new FrameStack();
    private Map<String, ClosureInterface> closureInterfaces = new LinkedHashMap<>();
    private List<Closure> closures = new LinkedList<>();
    private Map<String, Reference> references = new LinkedHashMap<>();
    private int closureCounter = 0;

    public void compile(ASTNode root) {
        Assembler a = root.compile(this, globalEnvironment);
        lineBuilder.append(a);
        try {
            lineBuilder.addHeader();
            lineBuilder.addFooter();
            lineBuilder.writeToFile("Main.j");
            fStack.dumpStack();
            dumpReferences();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void dumpReferences() throws IOException {
        for (Map.Entry<String, Reference> entry : references.entrySet()) {
            entry.getValue().dump();
        }
    }

    public Reference newReference(IType refType) {
        Reference ref = new Reference(refType);
        references.put(ref.getReferenceID(), ref);
        return ref;
    }

    public ClosureInterface newClosureInterface(TFun functionType) {
        final String cType = functionType.getClosureType();
        if (closureInterfaces.containsKey(cType)) {
            closureInterfaces.put(cType, new ClosureInterface(functionType));
        }
        return closureInterfaces.get(cType);
    }

    public FrameStack getfStack() {
        return fStack;
    }


}
