package compiler;

import ast.ASTNode;
import env.Environment;
import types.IType;
import types.TFun;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoreCompiler {

    private LineBuilder lineBuilder = new LineBuilder();
    private Environment<IType> globalEnvironment = new Environment<>();
    private FrameStack fStack = new FrameStack();
    private Map<String, Dumpable> dumpables = new LinkedHashMap<>();

    public void compile(ASTNode root) {
        Assembler a = root.compile(this, globalEnvironment);
        lineBuilder.append(a);
        try {
            lineBuilder.addHeader();
            lineBuilder.addFooter();
            lineBuilder.writeToFile("Main.j");
            fStack.dumpStack();
            dump();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void dump() throws IOException {
        for (Map.Entry<String, Dumpable> entry : dumpables.entrySet()) {
            entry.getValue().dump();
        }
    }

    public Reference newReference(IType refType) {
        Reference ref = new Reference(refType);
        dumpables.putIfAbsent(ref.getReferenceID(), ref);
        return ref;
    }

    public ClosureInterface newClosureInterface(TFun functionType) {
        final String cType = functionType.getClosureType();
        ClosureInterface closureInterface = new ClosureInterface(functionType);
        dumpables.putIfAbsent(cType, closureInterface);
        return closureInterface;
    }

    public Closure newClosure(ClosureInterface closureInterface) {
        final Closure closure = fStack.newClosureFrame(closureInterface);
        dumpables.putIfAbsent(closure.getFrameId(), closure);
        final Frame closureFrame = closure.getClosureFrame();
        dumpables.putIfAbsent(closureFrame.frameId, closureFrame);
        return closure;
    }

    public Closure newClosure(TFun type) {
        final ClosureInterface closureInterface = newClosureInterface(type);
        final Closure closure = newClosure(closureInterface);
        return closure;
    }


    public FrameStack getfStack() {
        return fStack;
    }


}
