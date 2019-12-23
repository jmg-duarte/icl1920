package value;

import ast.ASTNode;
import env.Environment;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VFunc implements IValue {
    private final List<String> parameters;
    private final ASTNode body;
    private final Environment<IValue> env;

    public VFunc(List<String> parameters, ASTNode body, Environment<IValue> env) {
        this.parameters = parameters;
        this.body = body;
        this.env = env;
    }

    public static VFunc check(IValue value) {
        if (!(value instanceof VFunc)) {
            throw new TypeErrorException();
        }
        return (VFunc) value;
    }

    public IValue apply(List<IValue> args) {
        Environment<IValue> innerScope = env.startScope();
        Iterator<String> paramIt = parameters.iterator();
        Iterator<IValue> argsIt = args.iterator();

        while (paramIt.hasNext() && argsIt.hasNext()) {
            env.associate(paramIt.next(), argsIt.next());
        }

        if (paramIt.hasNext()) {
            List<String> params = new LinkedList<>();
            paramIt.forEachRemaining(params::add);
            return new VFunc(params, body, innerScope);
        }

        return body.eval(innerScope);
    }

    @Override
    public String toString() {
        return String.format("Func(%s)", parameters.toString());
    }
}
