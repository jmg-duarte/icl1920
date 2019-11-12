package ast;

public class UnexpectedOperatorException extends RuntimeException {

    public UnexpectedOperatorException(String operator) {
        super("unexpected operator: " + operator);
    }
}
