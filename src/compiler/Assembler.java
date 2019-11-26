package compiler;

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
    public static final String IF_GREATER_EQ = "ifeq";
    public static final String IF_GREATER = "ifgt";
    public static final String IF_LESS = "iflt";
    public static final String IF_LESS_EQ = "ifle";
    public static final String GO_TO = "goto";
    public static final String BOOLEAN_FALSE = "sipush 0";
    public static final String BOOLEAN_TRUE = "sipush 1";

    private final String code;
    private final int stack;

    public Assembler(String code, int stack) {
        // this.code = code.endsWith("\n") ? code : code + "\n";
        this.code = code;
        this.stack = stack;
    }

    public String getCode() {
        return code;
    }

    public int getStack() {
        return stack;
    }

    @Override
    public String toString() {
        return code;
    }
}
