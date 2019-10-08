package env;

import ast.ASTNode;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private Map<String, Integer> scope;
    private Environment parent;

    public Environment() {
        scope = new HashMap<>();
    }

    public Environment(Environment parent) {
        this();
        this.parent = parent;
    }

    public int find(String id) {
        Environment env = this;
        while (true) {
            if (env == null) {
                throw new RuntimeException(id + " was not declared");
            }
            Integer value = env.scope.get(id);
            if (value == null) {
                env = env.parent;
            } else {
                return value;
            }
        }
    }

    public void associate(String id, int value) {
        scope.put(id, value);
    }

    public Environment startScope() {
        return new Environment(this);
    }

    public Environment endScope() {
        return parent;
    }

}
