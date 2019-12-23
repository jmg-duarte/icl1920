package compiler;

import types.IType;

public class Assembler {

    public static final String INTEGER_DIVISION = "idiv";
    public static final String INTEGER_MODULUS = "irem";
    public static final String INTEGER_MULTIPLICATION = "imul";
    public static final String INTEGER_ADDITION = "iadd";
    public static final String INTEGER_SUBTRACTION = "isub";
    public static final String BOOLEAN_AND = "iand";
    public static final String BOOLEAN_OR = "ior";
    public static final String IF_EQUALS = "ifeq";
    public static final String IF_NOT_EQUALS = "ifne";
    public static final String IF_GREATER_EQ = "ifge";
    public static final String IF_GREATER = "ifgt";
    public static final String IF_LESS = "iflt";
    public static final String IF_LESS_EQ = "ifle";
    public static final String GO_TO = "goto";
    public static final String BOOLEAN_FALSE = "iconst_0";
    public static final String BOOLEAN_TRUE = "iconst_1";

    private final String code;
    private final int stack;
    private final IType type;

    public Assembler(String code, int stack, IType type) {
        // this.code = code.endsWith("\n") ? code : code + "\n";
        this.code = code;
        this.stack = stack;
        this.type = type;
    }

    public IType getType() {
        return type;
    }

    public int getStack() {
        return stack;
    }

    @Override
    public String toString() {
        return code;
    }
}
