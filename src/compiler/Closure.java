package compiler;

import env.Environment;
import types.IType;

import java.util.List;

public class Closure {

    private final Environment environment;
    private final String closureId;
    private final IType bodyType;
    private final List<IType> paramTypes;

    public Closure(List<IType> paramTypes, IType bodyType, String closureId, Environment env) {
        this.environment = env;
        this.bodyType = bodyType;
        this.paramTypes = paramTypes;
        this.closureId = closureId;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public List<IType> getParams() {
        return paramTypes;
    }

    public IType getBodyType() {
        return bodyType;
    }

    public String getClosureId() {
        return closureId;
    }

    @Override
    public String toString() {
        return closureId;
    }
}
