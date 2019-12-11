package env;

import java.util.HashMap;
import java.util.Map;

public class Environment<T> {

    private Map<String, T> scope;
    private Environment<T> parent;
    private String name;

    public Environment() {
        scope = new HashMap<>();
    }

    public Environment(Environment<T> parent) {
        this();
        this.parent = parent;
    }

    public Environment(Environment<T> parent, String name) {
        this(parent);
        this.name = name;
    }

  /*  public Environment(Environment parent, String name) {
        this(parent);
        this.name = name;
    }*/

    public T find(String id) {
        Environment<T> env = this;
        while (true) {
            if (env == null) {
                throw new RuntimeException(id + " was not declared");
            }
            T value = env.scope.get(id);
            if (value == null) {
                env = env.parent;
            } else {
                return value;
            }
        }
    }

    public T findInScope(String id) {
        return scope.get(id);
    }

    public void associate(String id, T value) {
        scope.put(id, value);
    }

    public Environment<T> startScope() {
        return new Environment<>(this);
    }

    public Environment<T> startScope(String name) {
        return new Environment<>(this, name);
    }

    public Environment<T> endScope() {
        return parent;
    }

    public String getName() {
        return name;
    }
}
