package compiler;

public class Assembler {

    public static final String INTEGER_DIVISION = "idiv";
    public static final String INTEGER_MODULUS = "irem";
    public static final String INTEGER_MULTIPLICATION = "imul";
    public static final String INTEGER_ADDITION = "iadd";
    public static final String INTEGER_SUBTRACTION = "isub";

    private final String code;
    private final int stack;

    public Assembler(String code, int stack) {
        this.code = code.endsWith("\n") ? code : code + "\n";
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
