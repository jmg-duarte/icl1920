package compiler;

public class LabelMaker {

    private static int label;

    public static String getLabel() {
        return String.format("L%d", label++);
    }
}
