package value;

public class TypeErrorException extends RuntimeException {

    public TypeErrorException() {
        super("illegal arguments");
    }

    public TypeErrorException(String message) {
        super(message);
    }
}
