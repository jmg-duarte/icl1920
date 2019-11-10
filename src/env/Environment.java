package env;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private Map<String, Integer> scope;
    private Environment parent;
    private String name;

    public Environment() {
        scope = new HashMap<>();
    }

    public Environment(Environment parent) {
        this();
        this.parent = parent;
    }

    public Environment(Environment parent, String name) {
        this(parent);
        this.name = name;
    }

  /*  public Environment(Environment parent, String name) {
        this(parent);
        this.name = name;
    }*/

    public Integer find(String id) {
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

    public Integer findInScope(String id) {
        return scope.get(id);
    }

    public void associate(String id, int value) {
        scope.put(id, value);
    }

    public Environment startScope() {
        return new Environment(this);
    }

    public Environment startScope(String name) {
        return new Environment(this, name);
    }

    public Environment endScope() {
        return parent;
    }

    public String getName() {
        return name;
    }
}
