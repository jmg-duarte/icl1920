package compiler;

import env.Environment;
import types.IType;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.util.List;

public class Closure implements Dumpable{

    private final Environment environment;
    private final String closureId;
    private final IType bodyType;
    private final List<IType> paramTypes;
    private final LineBuilder lb = new LineBuilder();

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

    @Override
    public void dump() throws IOException {

    }
}
