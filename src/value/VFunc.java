package value;

import ast.ASTId;
import ast.ASTNode;
import env.Environment;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VFunc implements IValue {
    private final List<ASTId> parameters;
    private final ASTNode body;
    private final Environment env;

    public VFunc(List<ASTId> parameters, ASTNode body, Environment env) {
        this.parameters = parameters;
        this.body = body;
        this.env = env;
    }

    public IValue apply(List<IValue> args){
        Environment innerScope = env.startScope();
        Iterator<ASTId> paramIt = parameters.iterator();
        Iterator<IValue> argsIt = args.iterator();

        while(paramIt.hasNext() && argsIt.hasNext()){
            env.associate(paramIt.next().getId(), argsIt.next());
        }

        if(paramIt.hasNext()){
            List<ASTId> params = new LinkedList<>();
            paramIt.forEachRemaining(params::add);
            return new VFunc(params, body, innerScope);
        }

        return body.eval(innerScope);
    }

    public static VFunc check(IValue value) {
        if (!(value instanceof VFunc)) {
            throw new TypeErrorException();
        }
        return (VFunc) value;
    }

}
